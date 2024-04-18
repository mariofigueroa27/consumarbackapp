package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.HomoCamionService;
import com.fabrica.hutchisonspring.service.dto.HomoCamionDTO;
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
public class HomoCamionResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "homoCamion";

    private final HomoCamionService homoCamionService;

    public HomoCamionResource(HomoCamionService homoCamionService) {
        this.homoCamionService = homoCamionService;
    }

    @PostMapping("/camiones/file")
    public ResponseEntity<Map<String, Object>> saveCamiones(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = homoCamionService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity.ok().body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/camiones")
    public ResponseEntity<List<HomoCamionDTO>> getCamiones(Pageable pageable) {
        Page<HomoCamionDTO> page = homoCamionService.getCamiones(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/camiones")
    public ResponseEntity<HomoCamionDTO> save(@Valid @RequestBody HomoCamionDTO homoCamionDTO) throws URISyntaxException {
        if (homoCamionDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        HomoCamionDTO result = homoCamionService.save(homoCamionDTO);
        return ResponseEntity
                .created(new URI("/api/camiones/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/camiones")
    public ResponseEntity<HomoCamionDTO> update(@Valid @RequestBody HomoCamionDTO homoCamionDTO) {
        if (homoCamionDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        HomoCamionDTO result = homoCamionService.save(homoCamionDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/camiones/{id}")
    public ResponseEntity<HomoCamionDTO> getCamion(@PathVariable Long id) {
        Optional<HomoCamionDTO> result = homoCamionService.getCamion(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
