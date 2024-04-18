package com.fabrica.hutchisonspring.repository;

import java.time.Instant;

import com.fabrica.hutchisonspring.domain.CheckoutDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutDetailRepository extends JpaRepository<CheckoutDetail, Long> {
    // @formatter:off
    @Query("select c " +
            "from CheckoutDetail c " +
            "where ((?1 is null) or (c.dispatchDetail.vehicle.travel.ship.id = ?1)) " +
                "and ((?2 is null) or (c.dispatchDetail.vehicle.travel.id = ?2)) " +
                "and ((?3 is null) or (c.dispatchDetail.bl = ?3)) " +
                "and ((?4 is null) or (c.dispatchDetail.vehicle.chassis = ?4)) " +
                "and ((?5 is null) or (c.dispatchDetail.vehicle.tradeMark = ?5)) " +
                "and ((?6 is null) or (c.dispatchDetail.sdo = ?6)) " +
                "and (" +
                "((?7 is null) or (c.registeredAt >= ?7)) " +
                "and ((?8 is null) or (c.registeredAt <= ?8))" +
                ")")
    // @formatter:on
    Page<CheckoutDetail> findAllByFilters(Long shipId, Long travelId, String bl, String vehicleChassis,
            String vehicleTradeMark, String sdo, Instant registeredAtStart, Instant registeredAtEnd, Pageable pageable);
}
