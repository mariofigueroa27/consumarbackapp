package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.TravelService;
import com.fabrica.hutchisonspring.service.dto.TravelDTO;
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
public class TravelResource {

    private static final String ENTITY_NAME = "travel";

    @Value("${spring.application.name}")
    private String applicationName;

    private final TravelService travelService;

    public TravelResource(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping("/ships/{id}/travels/file")
    public ResponseEntity<Map<String, Object>> saveTravels(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = travelService.saveFromFile(id, file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/travels")
    public ResponseEntity<List<TravelDTO>> getTravels(Pageable pageable) {
        Page<TravelDTO> page = travelService.getTravels(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ships/{id}/travels")
    public ResponseEntity<List<TravelDTO>> getTravels(@PathVariable Long id, Pageable pageable) {
        Page<TravelDTO> page = travelService.getTravels(id, pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/travels")
    public ResponseEntity<TravelDTO> save(@Valid @RequestBody TravelDTO travelDTO) throws URISyntaxException {
        if (travelDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        TravelDTO result = travelService.save(travelDTO);
        return ResponseEntity
                .created(new URI("/api/travels/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/travels")
    public ResponseEntity<TravelDTO> update(@Valid @RequestBody TravelDTO travelDTO) {
        if (travelDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        TravelDTO result = travelService.save(travelDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/travels/{id}")
    public ResponseEntity<TravelDTO> getTravel(@PathVariable Long id) {
        Optional<TravelDTO> result = travelService.getTravel(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
