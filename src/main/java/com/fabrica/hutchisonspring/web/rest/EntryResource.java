package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.EntryService;
import com.fabrica.hutchisonspring.service.dto.EntryDTO;
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
public class EntryResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "entry";

    private final EntryService entryService;

    public EntryResource(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping("/entries/file")
    public ResponseEntity<Map<String, Object>> saveEntries(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = entryService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/entries")
    public ResponseEntity<List<EntryDTO>> getEntries(Pageable pageable) {
        Page<EntryDTO> page = entryService.getEntries(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/entries")
    public ResponseEntity<EntryDTO> save(@Valid @RequestBody EntryDTO entryDTO) throws URISyntaxException {
        if (entryDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        EntryDTO result = entryService.save(entryDTO);
        return ResponseEntity
                .created(new URI("/api/entries/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/entries")
    public ResponseEntity<EntryDTO> update(@Valid @RequestBody EntryDTO entryDTO) {
        if (entryDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        EntryDTO result = entryService.save(entryDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/entries/{id}")
    public ResponseEntity<EntryDTO> getEntry(@PathVariable Long id) {
        Optional<EntryDTO> result = entryService.getEntry(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
