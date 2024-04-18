package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.VentaBalanzaBaseService;
import com.fabrica.hutchisonspring.service.dto.VentaBalanzaBaseDTO;
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
public class VentaBalanzaBaseResource {

    private static final String ENTITY_NAME = "ventaBalanzaBase";

    @Value("${spring.application.name}")
    private String applicationName;

    private final VentaBalanzaBaseService ventaBalanzaBaseService;

    public VentaBalanzaBaseResource(VentaBalanzaBaseService ventaBalanzaBaseService) {
        this.ventaBalanzaBaseService = ventaBalanzaBaseService;
    }

    @PostMapping("/ventas/file")
    public ResponseEntity<Map<String, Object>> saveVentas(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = ventaBalanzaBaseService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/ventas")
    public ResponseEntity<List<VentaBalanzaBaseDTO>> getVentas(Pageable pageable) {
        Page<VentaBalanzaBaseDTO> page = ventaBalanzaBaseService.getVentas(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/ventas")
    public ResponseEntity<VentaBalanzaBaseDTO> save(@Valid @RequestBody VentaBalanzaBaseDTO ventaBalanzaBaseDTO) throws URISyntaxException {
        if (ventaBalanzaBaseDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        VentaBalanzaBaseDTO result = ventaBalanzaBaseService.save(ventaBalanzaBaseDTO);
        return ResponseEntity
                .created(new URI("/api/ventas/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/ventas")
    public ResponseEntity<VentaBalanzaBaseDTO> update(@Valid @RequestBody VentaBalanzaBaseDTO ventaBalanzaBaseDTO) {
        if (ventaBalanzaBaseDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        VentaBalanzaBaseDTO result = ventaBalanzaBaseService.save(ventaBalanzaBaseDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/ventas/{id}")
    public ResponseEntity<VentaBalanzaBaseDTO> getVenta(@PathVariable Long id) {
        Optional<VentaBalanzaBaseDTO> result = ventaBalanzaBaseService.getVenta(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
