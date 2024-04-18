package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.OrdenSurveyService;
import com.fabrica.hutchisonspring.service.dto.OrdenSurveyDTO;
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
public class OrderSurveyResource {

    private static final String ENTITY_NAME = "ordenSurvey";

    @Value("${spring.application.name}")
    private String applicationName;

    private final OrdenSurveyService ordenSurveyService;

    public OrderSurveyResource(OrdenSurveyService ordenSurveyService) {
        this.ordenSurveyService = ordenSurveyService;
    }

    @PostMapping("/surveys/file")
    public ResponseEntity<Map<String, Object>> saveSurveys(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = ordenSurveyService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/surveys")
    public ResponseEntity<List<OrdenSurveyDTO>> getSurveys(Pageable pageable) {
        Page<OrdenSurveyDTO> page = ordenSurveyService.getSurveys(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/surveys")
    public ResponseEntity<OrdenSurveyDTO> save(@Valid @RequestBody OrdenSurveyDTO ordenSurveyDTO) throws URISyntaxException {
        if (ordenSurveyDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        OrdenSurveyDTO result = ordenSurveyService.save(ordenSurveyDTO);
        return ResponseEntity
                .created(new URI("/api/surveys/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/surveys")
    public ResponseEntity<OrdenSurveyDTO> update(@Valid @RequestBody OrdenSurveyDTO ordenSurveyDTO) {
        if (ordenSurveyDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        OrdenSurveyDTO result = ordenSurveyService.save(ordenSurveyDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/surveys/{id}")
    public ResponseEntity<OrdenSurveyDTO> getSurvey(@PathVariable Long id) {
        Optional<OrdenSurveyDTO> result = ordenSurveyService.getSurvey(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
