package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import com.fabrica.hutchisonspring.service.EmployeeService;
import com.fabrica.hutchisonspring.service.dto.EmployeeDTO;
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
public class EmployeeResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "employee";

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees/file")
    public ResponseEntity<Map<String, Object>> saveEmployees(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = employeeService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity
                .ok()
                .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(Pageable pageable) {
        Page<EmployeeDTO> page = employeeService.getEmployees(pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> save(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        if (employeeDTO.getId() != null) {
            throw new RuntimeException("id already exists");
        }
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity
                .created(new URI("/api/employees/" + result.getId().toString()))
                .body(result);
    }

    @PutMapping("/employees")
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() == null) {
            throw new RuntimeException("id not exists");
        }
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        Optional<EmployeeDTO> result = employeeService.getEmployee(id);
        return result.map(resp -> ResponseEntity.ok().body(resp))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
