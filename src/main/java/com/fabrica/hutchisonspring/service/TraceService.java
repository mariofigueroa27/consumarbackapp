package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.repository.TraceRepository;
import com.fabrica.hutchisonspring.service.dto.TraceDTO;
import com.fabrica.hutchisonspring.service.mapper.TraceMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TraceService {
    private final TraceRepository traceRepository;

    private final TraceMapper traceMapper;

    public TraceService(TraceRepository traceRepository, TraceMapper traceMapper) {
        this.traceRepository = traceRepository;
        this.traceMapper = traceMapper;
    }

    @Transactional(readOnly = true)
    public Page<TraceDTO> getTraces(Long shipId, Long travelId, String bl, String vehicleChassis,
            String vehicleTradeMark, Boolean dispatched, Boolean exited, Pageable pageable) {
        return traceRepository
                .findAllByFilters(shipId, travelId, bl, vehicleChassis, vehicleTradeMark, dispatched, exited, pageable)
                .map(traceMapper::toDto);
    }
}
