package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.ShipService;
import com.fabrica.hutchisonspring.service.dto.ShipDTO;
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
public class ShipResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "ship";

    private final ShipService shipService;

    public ShipResource(ShipService shipService) {
        this.shipService = shipService;
    }

    @PostMapping("/ships/file")
    public ResponseEntity<Map<String, Object>> saveShips(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = shipService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/ships")
    public ResponseEntity<List<ShipDTO>> getShips(Pageable pageable, @RequestParam(required = false) String operation) {
        Page<ShipDTO> page;
        if (operation == null || operation.isEmpty()) {
            page = shipService.getShips(pageable);
        } else {
            page = shipService.getShips(operation, pageable);
        }
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/ships")
    public ResponseEntity<ShipDTO> save(@Valid @RequestBody ShipDTO shipDTO) throws URISyntaxException {
        if (shipDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        ShipDTO result = shipService.save(shipDTO);
        return ResponseEntity
                .created(new URI("/api/ships/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/ships")
    public ResponseEntity<ShipDTO> update(@Valid @RequestBody ShipDTO shipDTO) {
        if (shipDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        ShipDTO result = shipService.save(shipDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/ships/{id}")
    public ResponseEntity<ShipDTO> getShip(@PathVariable Long id) {
        Optional<ShipDTO> result = shipService.getShip(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
