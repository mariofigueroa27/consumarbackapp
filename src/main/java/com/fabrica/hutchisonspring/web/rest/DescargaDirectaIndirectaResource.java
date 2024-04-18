package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.DescargaDirectaIndirectaService;
import com.fabrica.hutchisonspring.service.dto.DescargaDirectaIndirectaDTO;
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
public class DescargaDirectaIndirectaResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "descargaDirectaIndirecta";

    private final DescargaDirectaIndirectaService descargaDirectaIndirectaService;

    public DescargaDirectaIndirectaResource(DescargaDirectaIndirectaService descargaDirectaIndirectaService) {
        this.descargaDirectaIndirectaService = descargaDirectaIndirectaService;
    }

    @PostMapping("/descargas/file")
    public ResponseEntity<Map<String, Object>> saveDescargas(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = descargaDirectaIndirectaService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/descargas")
    public ResponseEntity<List<DescargaDirectaIndirectaDTO>> getDescargas(Pageable pageable) {
        Page<DescargaDirectaIndirectaDTO> page = descargaDirectaIndirectaService.getDescargas(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/descargas")
    public ResponseEntity<DescargaDirectaIndirectaDTO> save(@Valid @RequestBody DescargaDirectaIndirectaDTO descargaDirectaIndirectaDTO) throws URISyntaxException {
        if (descargaDirectaIndirectaDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        DescargaDirectaIndirectaDTO result = descargaDirectaIndirectaService.save(descargaDirectaIndirectaDTO);
        return ResponseEntity
                .created(new URI("/api/descargas/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/descargas")
    public ResponseEntity<DescargaDirectaIndirectaDTO> update(@Valid @RequestBody DescargaDirectaIndirectaDTO descargaDirectaIndirectaDTO) {
        if (descargaDirectaIndirectaDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        DescargaDirectaIndirectaDTO result = descargaDirectaIndirectaService.save(descargaDirectaIndirectaDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/descargas/{id}")
    public ResponseEntity<DescargaDirectaIndirectaDTO> getDescarga(@PathVariable Long id) {
        Optional<DescargaDirectaIndirectaDTO> result = descargaDirectaIndirectaService.getDescarga(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
