package com.fabrica.hutchisonspring.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.fabrica.hutchisonspring.repository.CheckoutDetailRepository;
import com.fabrica.hutchisonspring.service.dto.CheckoutDetailDTO;
import com.fabrica.hutchisonspring.service.mapper.CheckoutDetailMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckoutDetailService {
    private final CheckoutDetailRepository checkoutDetailRepository;

    private final CheckoutDetailMapper checkoutDetailMapper;

    public CheckoutDetailService(CheckoutDetailRepository checkoutDetailRepository,
            CheckoutDetailMapper checkoutDetailMapper) {
        this.checkoutDetailRepository = checkoutDetailRepository;
        this.checkoutDetailMapper = checkoutDetailMapper;
    }

    @Transactional(readOnly = true)
    public Page<CheckoutDetailDTO> getCheckoutDetails(Long shipId, Long travelId, String bl, String vehicleChassis,
            String vehicleTradeMark, String sdo, Instant registeredAt, Pageable pageable) {
        return checkoutDetailRepository
                .findAllByFilters(shipId, travelId, bl, vehicleChassis, vehicleTradeMark, sdo, registeredAt,
                        registeredAt != null ? registeredAt.plus(1, ChronoUnit.DAYS) : null, pageable)
                .map(checkoutDetailMapper::toDto);
    }
}
