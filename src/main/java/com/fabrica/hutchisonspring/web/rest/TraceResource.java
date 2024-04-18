package com.fabrica.hutchisonspring.web.rest;

import java.util.List;

import com.fabrica.hutchisonspring.service.TraceService;
import com.fabrica.hutchisonspring.service.dto.TraceDTO;
import com.fabrica.hutchisonspring.utils.PaginationUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TraceResource {
    private final TraceService traceService;

    public TraceResource(TraceService traceService) {
        this.traceService = traceService;
    }

    @GetMapping("/traces")
    public ResponseEntity<List<TraceDTO>> getTraces(
    // @formatter:off
        @RequestParam(required = false) Long shipId,
        @RequestParam(required = false) Long travelId,
        @RequestParam(required = false) String bl,
        @RequestParam(required = false) String vehicleChassis,
        @RequestParam(required = false) String vehicleTradeMark,
        @RequestParam(required = false) Boolean dispatched,
        @RequestParam(required = false) Boolean exited,
        Pageable pageable
        // @formatter:on
    ) {
        Page<TraceDTO> page = traceService.getTraces(shipId, travelId, bl, vehicleChassis, vehicleTradeMark, dispatched,
                exited, pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
