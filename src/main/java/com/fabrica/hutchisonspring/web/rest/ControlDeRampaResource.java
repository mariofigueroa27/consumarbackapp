package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.repository.summary.DateWorkingDaySelect;
import com.fabrica.hutchisonspring.repository.summary.LevelSelect;
import com.fabrica.hutchisonspring.repository.summary.TradeMarkSelect;
import com.fabrica.hutchisonspring.service.ControlDeRampaService;
import com.fabrica.hutchisonspring.service.dto.ControlDeRampaDTO;
import com.fabrica.hutchisonspring.utils.BatchUtils;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ControlDeRampaResource {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "ControlDeRampa";

    private final ControlDeRampaService controlDeRampaService;

    public ControlDeRampaResource(ControlDeRampaService controlDeRampaService) {
        this.controlDeRampaService = controlDeRampaService;
    }

    @GetMapping("/ramps/level")
    public ResponseEntity<List<LevelSelect>> getLevelGroup(
            @RequestParam(required = false) String chassis,
            @RequestParam(required = false) String workingDay,
            @RequestParam(required = false) Long serviceOrderId,
            @RequestParam(required = false) String tradeMark,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startInstant,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endInstant) {
        List<LevelSelect> list = controlDeRampaService.findGroupByLevel(chassis, workingDay, serviceOrderId, tradeMark, level, startDate, endDate, startInstant, endInstant);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/ramps/date")
    public ResponseEntity<List<DateWorkingDaySelect>> getDateGroup(
            @RequestParam(required = false) String chassis,
            @RequestParam(required = false) String workingDay,
            @RequestParam(required = false) Long serviceOrderId,
            @RequestParam(required = false) String tradeMark,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startInstant,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endInstant) {
        List<DateWorkingDaySelect> list = controlDeRampaService.findGroupByDate(chassis, workingDay, serviceOrderId, tradeMark, level, startDate, endDate, startInstant, endInstant);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/ramps/tradeMark")
    public ResponseEntity<List<TradeMarkSelect>> getTradeMarkGroup(
            @RequestParam(required = false) String chassis,
            @RequestParam(required = false) String workingDay,
            @RequestParam(required = false) Long serviceOrderId,
            @RequestParam(required = false) String tradeMark,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startInstant,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endInstant) {
        List<TradeMarkSelect> list = controlDeRampaService.findGroupByTradeMark(chassis, workingDay, serviceOrderId, tradeMark, level, startDate, endDate, startInstant, endInstant);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/ramps")
    public ResponseEntity<List<ControlDeRampaDTO>> getRamps(
            @RequestParam(required = false) String chassis,
            @RequestParam(required = false) String workingDay,
            @RequestParam(required = false) Long serviceOrderId,
            @RequestParam(required = false) String tradeMark,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startInstant,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endInstant,
            Pageable pageable) {
        Page<ControlDeRampaDTO> page = controlDeRampaService.findFilteredControles(chassis, workingDay, serviceOrderId, tradeMark, level, startDate, endDate, startInstant, endInstant, pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHeaders(page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/ramps/file")
    public ResponseEntity<Map<String, Object>> saveRamps(@RequestParam("file") MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            var start = Instant.now();
            var resultFile = controlDeRampaService.saveFromFile(file);
            return ResponseEntity
                    .ok()
                    .body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME, resultFile, start));
        }
        return ResponseEntity.ok().body(BatchUtils.createBatchResponse(applicationName, ENTITY_NAME));
    }
}
