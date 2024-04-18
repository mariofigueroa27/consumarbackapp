package com.fabrica.hutchisonspring.web.rest;

import java.time.Instant;
import java.util.List;

import com.fabrica.hutchisonspring.service.DispatchDetailService;
import com.fabrica.hutchisonspring.service.dto.DispatchDetailDTO;
import com.fabrica.hutchisonspring.utils.PaginationUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DispatchDetailResource {
    private final DispatchDetailService dispatchDetailService;

    public DispatchDetailResource(DispatchDetailService dispatchDetailService) {
        this.dispatchDetailService = dispatchDetailService;
    }

    @GetMapping("/dispatchDetails")
    public ResponseEntity<List<DispatchDetailDTO>> getDispatchDetails(
    // @formatter:off
        @RequestParam(required = false) Long shipId,
        @RequestParam(required = false) Long travelId,
        @RequestParam(required = false) String bl,
        @RequestParam(required = false) String vehicleChassis,
        @RequestParam(required = false) String vehicleTradeMark,
        @RequestParam(required = false) String sdo,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant registeredAt,
        Pageable pageable
        // @formatter:on
    ) {
        Page<DispatchDetailDTO> page = dispatchDetailService.getDispatchDetails(shipId, travelId, bl, vehicleChassis,
                vehicleTradeMark, sdo, registeredAt, pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
