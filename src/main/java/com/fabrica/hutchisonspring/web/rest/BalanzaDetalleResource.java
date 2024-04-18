package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.BalanzaDetalleService;
import com.fabrica.hutchisonspring.service.dto.BalanzaDetalleDTO;
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
public class BalanzaDetalleResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "balanzaDetalle";

    private final BalanzaDetalleService balanzaDetalleService;

    public BalanzaDetalleResource(BalanzaDetalleService balanzaDetalleService) {
        this.balanzaDetalleService = balanzaDetalleService;
    }

    @PostMapping("/balanza-detalles/file")
    public ResponseEntity<Map<String, Object>> saveDetalles(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = balanzaDetalleService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/balanza-detalles")
    public ResponseEntity<List<BalanzaDetalleDTO>> getDetalles(Pageable pageable) {
        Page<BalanzaDetalleDTO> page = balanzaDetalleService.getDetalles(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/balanza-detalles")
    public ResponseEntity<BalanzaDetalleDTO> save(@Valid @RequestBody BalanzaDetalleDTO balanzaDetalleDTO) throws URISyntaxException {
        if (balanzaDetalleDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        BalanzaDetalleDTO result = balanzaDetalleService.save(balanzaDetalleDTO);
        return ResponseEntity
                .created(new URI("/api/balanzaDetalles/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/balanza-detalles")
    public ResponseEntity<BalanzaDetalleDTO> update(@Valid @RequestBody BalanzaDetalleDTO balanzaDetalleDTO) {
        if (balanzaDetalleDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        BalanzaDetalleDTO result = balanzaDetalleService.save(balanzaDetalleDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/balanza-detalles/{id}")
    public ResponseEntity<BalanzaDetalleDTO> getDetalle(@PathVariable Long id) {
        Optional<BalanzaDetalleDTO> result = balanzaDetalleService.getDetalle(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
