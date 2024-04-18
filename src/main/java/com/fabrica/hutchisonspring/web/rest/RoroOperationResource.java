package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.service.RoroOperationService;
import com.fabrica.hutchisonspring.service.dto.RoroOperationDTO;
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

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoroOperationResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "operation";

    private final RoroOperationService roroOperationService;

    public RoroOperationResource(RoroOperationService roroOperationService) {
        this.roroOperationService = roroOperationService;
    }

    @PostMapping("/travels/{id}/orders/{orderId}/operations/file")
    public ResponseEntity<Map<String, Object>> saveOperations(@PathVariable Long id, @PathVariable Long orderId, @RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = roroOperationService.saveFromFile(id, orderId, file);
            return new ResponseEntity<>(
                    BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start),
                    resultFile.contains("-ok") ? HttpStatus.OK : HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.badRequest().body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/operations/associate")
    @ResponseStatus(HttpStatus.OK)
    public void associate() {
        roroOperationService.associate();
    }

    @GetMapping("/operations")
    public ResponseEntity<List<RoroOperationDTO>> getOperations(Pageable pageable) {
        Page<RoroOperationDTO> page = roroOperationService.findOperations(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @DeleteMapping("/operations/clean")
    public void cleanOperations() {
        roroOperationService.deleteOperationWithNoChassis();
    }
}
