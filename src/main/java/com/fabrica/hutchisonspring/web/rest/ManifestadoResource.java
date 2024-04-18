package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.ManifestadoService;
import com.fabrica.hutchisonspring.service.dto.ManifestadoDTO;
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
public class ManifestadoResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "manifestado";

    private final ManifestadoService manifestadoService;

    public ManifestadoResource(ManifestadoService manifestadoService) {
        this.manifestadoService = manifestadoService;
    }

    @PostMapping("/manifestados/file")
    public ResponseEntity<Map<String, Object>> saveManifestados(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = manifestadoService.saveFromFile(file);
            return ResponseEntity.ok().body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity.ok().body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/manifestados")
    public ResponseEntity<List<ManifestadoDTO>> getManifestados(Pageable pageable) {
        Page<ManifestadoDTO> page = manifestadoService.getManifestados(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/manifestados")
    public ResponseEntity<ManifestadoDTO> save(@Valid @RequestBody ManifestadoDTO manifestadoDTO) throws URISyntaxException {
        if (manifestadoDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        ManifestadoDTO result = manifestadoService.save(manifestadoDTO);
        return ResponseEntity
                .created(new URI("/api/manifestados/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/manifestados")
    public ResponseEntity<ManifestadoDTO> update(@Valid @RequestBody ManifestadoDTO manifestadoDTO) {
        if (manifestadoDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        ManifestadoDTO result = manifestadoService.save(manifestadoDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/manifestados/{id}")
    public ResponseEntity<ManifestadoDTO> getManifestado(@PathVariable Long id) {
        Optional<ManifestadoDTO> result = manifestadoService.getManifestado(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
