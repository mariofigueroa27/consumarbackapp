package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.ControlDeRampa;
import com.fabrica.hutchisonspring.repository.summary.DateWorkingDaySelect;
import com.fabrica.hutchisonspring.repository.summary.LevelSelect;
import com.fabrica.hutchisonspring.repository.summary.TradeMarkSelect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public interface ControlDeRampaRepository extends JpaRepository<ControlDeRampa, Long> {

    /**
     * {@code ?1}: workingDay String
     * <br>
     * {@code ?2}: serviceOrderId Long
     * <br>
     * {@code ?3}: tradeMark String
     * <br>
     * {@code ?4}: level Integer
     * <br>
     * {@code ?5}: startDate Date
     * <br>
     * {@code ?6}: endDate Date
     * <br>
     * {@code ?7}: startInstant Instant
     * <br>
     * {@code ?8}: endInstant Instant
     */
    String FILTER_WHERE_QUERY = "where ((?1 is null) or (c.chassis = ?1)) " +
            "and ((?2 is null) or (c.workingDay = ?2)) " +
            "and ((?3 is null) or (c.serviceOrderId = ?3)) " +
            "and ((?4 is null) or (c.tradeMark = ?4)) " +
            "and ((?5 is null) or (c.level = ?5)) " +
            "and (" +
            "((?6 is null) or (c.date >= cast(?6 as date))) " +
            "and ((?7 is null) or (c.date <= cast(?7 as date)))" +
            ") " +
            "and (" +
            "((?8 is null) or (c.readingTime >= ?8)) " +
            "and ((?9 is null) or (c.readingTime <= ?9)) " +
            ") ";

    @Query("select " +
            "new com.fabrica.hutchisonspring.repository.summary.DateWorkingDaySelect(count(c.chassis), c.date, c.workingDay) " +
            "from ControlDeRampa c " +
            FILTER_WHERE_QUERY +
            "group by c.date, c.workingDay")
    List<DateWorkingDaySelect> dateSelectByFilters(String chassis, String workingDay, Long serviceOrderId, String tradeMark, Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant);

    @Query("select new com.fabrica.hutchisonspring.repository.summary.TradeMarkSelect(count(c.chassis), c.tradeMark) " +
            "from ControlDeRampa c " +
            FILTER_WHERE_QUERY +
            "group by c.tradeMark")
    List<TradeMarkSelect> tradeMarkSelectByFilters(String chassis, String workingDay, Long serviceOrderId, String tradeMark, Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant);

    @Query("select new com.fabrica.hutchisonspring.repository.summary.LevelSelect(count(c.chassis), c.level) " +
            "from ControlDeRampa c " +
            FILTER_WHERE_QUERY +
            "group by c.level")
    List<LevelSelect> levelSelectByFilters(String chassis, String workingDay, Long serviceOrderId, String tradeMark, Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant);

    @Query("select c from ControlDeRampa c " +
            FILTER_WHERE_QUERY
    )
    Page<ControlDeRampa> findByFilters(String chassis, String workingDay, Long serviceOrderId, String tradeMark, Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant, Pageable pageable);
}
