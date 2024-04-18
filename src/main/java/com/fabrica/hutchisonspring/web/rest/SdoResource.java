package com.fabrica.hutchisonspring.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.fabrica.hutchisonspring.service.SdoService;
import com.fabrica.hutchisonspring.service.dto.SdoDTO;
import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class SdoResource {
    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "sdo";

    private final SdoService sdoService;

    public SdoResource(SdoService sdoService) {
        this.sdoService = sdoService;
    }

    @PostMapping("/travels/{id}/orders/{orderId}/sdos/file")
    public ResponseEntity<Map<String, Object>> saveOperations(@PathVariable Long id, @PathVariable Long orderId,
            @RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = sdoService.saveFromFile(id, orderId, file);
            return new ResponseEntity<>(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start),
                    resultFile.contains("-ok") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.badRequest().body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/sdos")
    public ResponseEntity<List<SdoDTO>> getOperations(Pageable pageable) {
        Page<SdoDTO> page = sdoService.getSdos(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/sdos")
    public ResponseEntity<SdoDTO> save(@Valid @RequestBody SdoDTO sdoDTO) throws URISyntaxException {
        if (sdoDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        SdoDTO result = sdoService.save(sdoDTO);
        return ResponseEntity.created(new URI("/api/surveys/" + result.getId().toString())).body(result);
    }

    @PutMapping("/sdos")
    public ResponseEntity<SdoDTO> update(@Valid @RequestBody SdoDTO sdoDTO) {
        if (sdoDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        SdoDTO result = sdoService.save(sdoDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/sdos/{id}")
    public ResponseEntity<SdoDTO> getSdo(@PathVariable Long id) {
        Optional<SdoDTO> result = sdoService.getSdo(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
