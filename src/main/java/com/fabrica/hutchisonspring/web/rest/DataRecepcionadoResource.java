package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.service.DataRecepcionadoService;
import com.fabrica.hutchisonspring.service.dto.DataRecepcionadoDTO;
import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
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
public class DataRecepcionadoResource {

    private static final String ENTITY_NAME = "dataRecepcionado";

    @Value("${spring.application.name}")
    private String applicationName;

    private final DataRecepcionadoService dataRecepcionadoService;

    public DataRecepcionadoResource(DataRecepcionadoService dataRecepcionadoService) {
        this.dataRecepcionadoService = dataRecepcionadoService;
    }

    @PostMapping("/data-recepcionados/file")
    public ResponseEntity<Map<String, Object>> saveRecepcionados(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = dataRecepcionadoService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @PostMapping("/data-recepcionados")
    public ResponseEntity<DataRecepcionadoDTO> save(@Valid @RequestBody DataRecepcionadoDTO dataRecepcionadoDTO) throws URISyntaxException {
        if (dataRecepcionadoDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        DataRecepcionadoDTO result = dataRecepcionadoService.save(dataRecepcionadoDTO);
        return ResponseEntity
                .created(new URI("/api/dataRecepcionados/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/data-recepcionados")
    public ResponseEntity<DataRecepcionadoDTO> update(@Valid @RequestBody DataRecepcionadoDTO dataRecepcionadoDTO) {
        if (dataRecepcionadoDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        DataRecepcionadoDTO result = dataRecepcionadoService.save(dataRecepcionadoDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/data-recepcionados")
    public ResponseEntity<List<DataRecepcionadoDTO>> getRecepcionados(Pageable pageable) {
        Page<DataRecepcionadoDTO> page = dataRecepcionadoService.getDataRecepcionados(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/data-recepcionados/{id}")
    public ResponseEntity<DataRecepcionadoDTO> getRecepcionado(@PathVariable Long id) {
        Optional<DataRecepcionadoDTO> result = dataRecepcionadoService.getDataRecepcionado(id);
        return result
                .map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
