package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.DatoEquipoService;
import com.fabrica.hutchisonspring.service.dto.DatoEquipoDTO;
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
public class DatoEquipoResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "datoEquipo";

    private final DatoEquipoService datoEquipoService;

    public DatoEquipoResource(DatoEquipoService datoEquipoService) {
        this.datoEquipoService = datoEquipoService;
    }

    @PostMapping("/equipos/file")
    public ResponseEntity<Map<String, Object>> saveEquipos(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = datoEquipoService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/equipos")
    public ResponseEntity<List<DatoEquipoDTO>> getEquipos(Pageable pageable) {
        Page<DatoEquipoDTO> page = datoEquipoService.getEquipos(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/equipos")
    public ResponseEntity<DatoEquipoDTO> save(@Valid @RequestBody DatoEquipoDTO datoEquipoDTO) throws URISyntaxException {
        if (datoEquipoDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        DatoEquipoDTO result = datoEquipoService.save(datoEquipoDTO);
        return ResponseEntity
                .created(new URI("/api/equipos/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/equipos")
    public ResponseEntity<DatoEquipoDTO> update(@Valid @RequestBody DatoEquipoDTO datoEquipoDTO) {
        if (datoEquipoDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        DatoEquipoDTO result = datoEquipoService.save(datoEquipoDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<DatoEquipoDTO> getEquipo(@PathVariable Long id) {
        Optional<DatoEquipoDTO> result = datoEquipoService.getEquipo(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
