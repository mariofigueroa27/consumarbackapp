package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.service.OrderService;
import com.fabrica.hutchisonspring.service.dto.ServiceOrderDTO;
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
public class ServiceOrderResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "serviceOrder";

    private final OrderService orderService;

    public ServiceOrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/service-orders/file")
    public ResponseEntity<Map<String, Object>> saveOrders(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = orderService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/service-orders")
    public ResponseEntity<List<ServiceOrderDTO>> getOrders(Pageable pageable) {
        Page<ServiceOrderDTO> page = orderService.getOrders(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/service-orders")
    public ResponseEntity<ServiceOrderDTO> save(@Valid @RequestBody ServiceOrderDTO serviceOrderDTO) throws URISyntaxException {
        if (serviceOrderDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        ServiceOrderDTO result = orderService.save(serviceOrderDTO);
        return ResponseEntity
                .created(new URI("/api/ships/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/service-orders")
    public ResponseEntity<ServiceOrderDTO> update(@Valid @RequestBody ServiceOrderDTO serviceOrderDTO) {
        if (serviceOrderDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        ServiceOrderDTO result = orderService.save(serviceOrderDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/service-orders/{id}")
    public ResponseEntity<ServiceOrderDTO> getOrder(@PathVariable Long id) {
        Optional<ServiceOrderDTO> result = orderService.getOrder(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
