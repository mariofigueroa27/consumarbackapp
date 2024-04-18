package com.fabrica.hutchisonspring.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.fabrica.hutchisonspring.domain.DispatchDetail;
import com.fabrica.hutchisonspring.repository.DispatchDetailRepository;
import com.fabrica.hutchisonspring.service.dto.DispatchDetailDTO;
import com.fabrica.hutchisonspring.service.mapper.DispatchDetailMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DispatchDetailService {
    private final DispatchDetailMapper dispatchDetailMapper;

    private final DispatchDetailRepository dispatchDetailRepository;

    public DispatchDetailService(DispatchDetailMapper dispatchDetailMapper,
            DispatchDetailRepository dispatchDetailRepository) {
        this.dispatchDetailMapper = dispatchDetailMapper;
        this.dispatchDetailRepository = dispatchDetailRepository;
    }

    @Transactional(readOnly = true)
    public Page<DispatchDetailDTO> getDispatchDetails(Long shipId, Long travelId, String bl, String vehicleChassis,
            String vehicleTradeMark, String sdo, Instant registeredAt, Pageable pageable) {
        return dispatchDetailRepository
                .findAllByFilters(shipId, travelId, bl, vehicleChassis, vehicleTradeMark, sdo, registeredAt,
                        registeredAt != null ? registeredAt.plus(1, ChronoUnit.DAYS) : null, pageable)
                .map(dispatchDetailMapper::toDto);
    }
}
