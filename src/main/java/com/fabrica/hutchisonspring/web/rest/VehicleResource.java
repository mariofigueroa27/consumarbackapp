package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.VehicleService;
import com.fabrica.hutchisonspring.service.dto.VehicleDTO;
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
public class VehicleResource {

    private static final String ENTITY_NAME = "vehicle";

    @Value("${spring.application.name}")
    private String applicationName;

    private final VehicleService vehicleService;

    public VehicleResource(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/travels/{id}/orders/{orderId}/vehicles/file")
    public ResponseEntity<Map<String, Object>> saveVehicles(@PathVariable Long id, @PathVariable Long orderId, @RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = vehicleService.saveFromFile(id, orderId, file);
            return new ResponseEntity<>(
                    BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start),
                    resultFile.contains("-ok") ? HttpStatus.OK : HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity
                .badRequest()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDTO>> getVehicles(Pageable pageable) {
        Page<VehicleDTO> page = vehicleService.getVehicles(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/travels/{id}/vehicles")
    public ResponseEntity<List<VehicleDTO>> getVehicles(@PathVariable Long id, Pageable pageable) {
        Page<VehicleDTO> page = vehicleService.getVehicles(id, pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> save(@Valid @RequestBody VehicleDTO vehicleDTO) throws URISyntaxException {
        if (vehicleDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        VehicleDTO result = vehicleService.save(vehicleDTO);
        return ResponseEntity
                .created(new URI("/api/vehicles/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/vehicles/labelled")
    @ResponseStatus(HttpStatus.OK)
    public void labelledVehicles(@RequestBody List<Long> ids) {
        vehicleService.updateLabels(ids);
    }

    @PutMapping("/vehicles")
    public ResponseEntity<VehicleDTO> update(@Valid @RequestBody VehicleDTO vehicleDTO) {
        if (vehicleDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        VehicleDTO result = vehicleService.save(vehicleDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        Optional<VehicleDTO> result = vehicleService.getVehicle(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
