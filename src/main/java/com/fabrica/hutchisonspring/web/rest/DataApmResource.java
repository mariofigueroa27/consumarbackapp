package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.service.DataApmService;
import com.fabrica.hutchisonspring.service.dto.DataApmDTO;
import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
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
public class DataApmResource {

    private static final String ENTITY_NAME = "dataApm";

    @Value("${spring.application.name}")
    private String applicationName;

    private final DataApmService dataApmService;

    public DataApmResource(DataApmService dataApmService) {
        this.dataApmService = dataApmService;
    }

    @PostMapping("/datas-apm/file")
    public ResponseEntity<Map<String, Object>> saveDatasApm(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = dataApmService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));

    }

    @GetMapping("/datas-apm")
    public ResponseEntity<List<DataApmDTO>> getDatasApm(Pageable pageable) {
        Page<DataApmDTO> page = dataApmService.getDatasApm(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/datas-apm/{id}")
    public ResponseEntity<DataApmDTO> getDataApm(@PathVariable Long id) {
        Optional<DataApmDTO> result = dataApmService.getDataApm(id);
        return result
                .map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/datas-apm")
    public ResponseEntity<DataApmDTO> save(@Valid @RequestBody DataApmDTO dataApmDTO) throws URISyntaxException {
        if (dataApmDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        DataApmDTO result = dataApmService.save(dataApmDTO);
        return ResponseEntity
                .created(new URI("/api/datasApm/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/datas-apm")
    public ResponseEntity<DataApmDTO> update(@Valid @RequestBody DataApmDTO dataApmDTO) {
        if (dataApmDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        DataApmDTO result = dataApmService.save(dataApmDTO);
        return ResponseEntity.ok().body(result);
    }
}
