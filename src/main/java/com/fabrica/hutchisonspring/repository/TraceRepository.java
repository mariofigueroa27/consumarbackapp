package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Trace;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TraceRepository extends JpaRepository<Trace, Long> {
    // @formatter:off
    @Query("select t " +
            "from Trace t " +
            "where ((?1 is null) or (t.vehicle.travel.ship.id = ?1)) " +
                "and ((?2 is null) or (t.vehicle.travel.id = ?2)) " +
                "and ((?3 is null) or (t.bl = ?3)) " +
                "and ((?4 is null) or (t.vehicle.chassis = ?4)) " +
                "and ((?5 is null) or (t.vehicle.tradeMark = ?5)) " +
                "and ((?6 is null) or (t.dispatch = ?6)) " +
                "and ((?7 is null) or (t.apmtcExit = ?7))"
    )
    // @formatter:on
    Page<Trace> findAllByFilters(Long shipId, Long travelId, String bl, String vehicleChassis, String vehicleTradeMark,
            Boolean dispatched, Boolean exited, Pageable pageable);
}
