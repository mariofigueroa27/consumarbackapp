package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.SiloService;
import com.fabrica.hutchisonspring.service.dto.SiloDTO;
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
public class SiloResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "silo";

    private final SiloService siloService;

    public SiloResource(SiloService siloService) {
        this.siloService = siloService;
    }

    @PostMapping("/silos/file")
    public ResponseEntity<Map<String, Object>> saveSilos(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = siloService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/silos")
    public ResponseEntity<List<SiloDTO>> getSilos(Pageable pageable) {
        Page<SiloDTO> page = siloService.getSilos(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/silos")
    public ResponseEntity<SiloDTO> save(@Valid @RequestBody SiloDTO siloDTO) throws URISyntaxException {
        if (siloDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        SiloDTO result = siloService.save(siloDTO);
        return ResponseEntity
                .created(new URI("/api/silos/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/silos")
    public ResponseEntity<SiloDTO> update(@Valid @RequestBody SiloDTO siloDTO) {
        if (siloDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        SiloDTO result = siloService.save(siloDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/silos/{id}")
    public ResponseEntity<SiloDTO> getSilo(@PathVariable Long id) {
        Optional<SiloDTO> result = siloService.getSilo(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
