package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.DatoParalizacionService;
import com.fabrica.hutchisonspring.service.dto.DatoParalizacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DatoParalizacionResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "datoParalizacion";

    private final DatoParalizacionService datoParalizacionService;

    public DatoParalizacionResource(DatoParalizacionService datoParalizacionService) {
        this.datoParalizacionService = datoParalizacionService;
    }

    @PostMapping("/paralizaciones/file")
    public ResponseEntity<Map<String, Object>> saveParalizaciones(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = datoParalizacionService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/paralizaciones")
    public ResponseEntity<List<DatoParalizacionDTO>> getParalizaciones(Pageable pageable) {
        Page<DatoParalizacionDTO> page = datoParalizacionService.getParalizaciones(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/paralizaciones")
    public ResponseEntity<DatoParalizacionDTO> save(@Valid @RequestBody DatoParalizacionDTO datoParalizacionDTO) throws URISyntaxException {
        if (datoParalizacionDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        DatoParalizacionDTO result = datoParalizacionService.save(datoParalizacionDTO);
        return ResponseEntity
                .created(new URI("/api/paralizaciones/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/paralizaciones")
    public ResponseEntity<DatoParalizacionDTO> update(@Valid @RequestBody DatoParalizacionDTO datoParalizacionDTO) {
        if (datoParalizacionDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        DatoParalizacionDTO result = datoParalizacionService.save(datoParalizacionDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/paralizaciones/{id}")
    public ResponseEntity<DatoParalizacionDTO> getParalizacion(@PathVariable Long id) {
        Optional<DatoParalizacionDTO> result = datoParalizacionService.getParalizacion(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
