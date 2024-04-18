package com.fabrica.hutchisonspring.repository;

import java.time.Instant;

import com.fabrica.hutchisonspring.domain.DispatchDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchDetailRepository extends JpaRepository<DispatchDetail, Long> {
    // @formatter:off
    @Query("select d " +
            "from DispatchDetail d " +
            "where ((?1 is null) or (d.vehicle.travel.ship.id = ?1)) " +
                "and ((?2 is null) or (d.vehicle.travel.id = ?2)) " +
                "and ((?3 is null) or (d.bl = ?3)) " +
                "and ((?4 is null) or (d.vehicle.chassis = ?4)) " +
                "and ((?5 is null) or (d.vehicle.tradeMark = ?5)) " +
                "and ((?6 is null) or (d.sdo = ?6)) " +
                "and (" +
                "((?7 is null) or (d.registeredAt >= ?7)) " +
                "and ((?8 is null) or (d.registeredAt <= ?8))" +
                ")")
    // @formatter:on
    Page<DispatchDetail> findAllByFilters(Long shipId, Long travelId, String bl, String vehicleChassis,
            String vehicleTradeMark, String sdo, Instant registeredAtStart, Instant registeredAtEnd, Pageable pageable);
}
