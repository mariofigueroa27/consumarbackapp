package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.ControlDeRampa;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.summary.DateWorkingDaySelect;
import com.fabrica.hutchisonspring.repository.summary.LevelSelect;
import com.fabrica.hutchisonspring.repository.summary.TradeMarkSelect;
import com.fabrica.hutchisonspring.repository.ControlDeRampaRepository;
import com.fabrica.hutchisonspring.service.dto.ControlDeRampaDTO;
import com.fabrica.hutchisonspring.service.mapper.ControlDeRampaMapper;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.FileManager;
import com.fabrica.hutchisonspring.utils.vm.ColumnMetaData;
import com.fabrica.hutchisonspring.utils.vm.ValidatorItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.sql.Types;
import java.util.Date;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class ControlDeRampaService {

    private static final ColumnMetaData[] DATA_FORMAT = { new ColumnMetaData(1, "IDControlDeRampa", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "IDJob", Types.BIGINT, 0, 0),
            new ColumnMetaData(3, "OrdenDeServicio", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "Operacion", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "Fecha", Types.VARCHAR, 0, 0), new ColumnMetaData(6, "Jornada", Types.VARCHAR, 0, 0),
            new ColumnMetaData(7, "Nivel", Types.INTEGER, 0, 0), new ColumnMetaData(8, "Chasis", Types.VARCHAR, 0, 0),
            new ColumnMetaData(9, "Marca", Types.VARCHAR, 0, 0),
            new ColumnMetaData(10, "HoraDeLectura", Types.VARCHAR, 0, 0),
            new ColumnMetaData(11, "service_order_id", Types.BIGINT, 0, 0),
            new ColumnMetaData(12, "vehicle_id", Types.BIGINT, 0, 0),
            new ColumnMetaData(13, "OperacionRampa", Types.VARCHAR, 0, 0) };

    private final BatchRepository<ControlDeRampa> batchRepository;
    private final ControlDeRampaRepository controlDeRampaRepository;
    private final ControlDeRampaMapper controlDeRampaMapper;

    private final FileManager fileManager;

    public ControlDeRampaService(BatchRepository<ControlDeRampa> batchRepository,
            ControlDeRampaRepository controlDeRampaRepository, ControlDeRampaMapper controlDeRampaMapper,
            FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.controlDeRampaRepository = controlDeRampaRepository;
        this.controlDeRampaMapper = controlDeRampaMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<ControlDeRampa> rampas = ExcelUtils.sheetToEntities(ControlDeRampa.class, file.getInputStream(),
                    "RAMPA", true, false);
            if (rampas == null || rampas.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(rampas);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<ControlDeRampa>> items = (List<ValidatorItem<ControlDeRampa>>) validated
                    .get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(ControlDeRampa.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(ControlDeRampa.class, DATA_FORMAT, false);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<ControlDeRampaDTO> findFilteredControles(String chassis, String workingDay, Long serviceOrderId,
            String tradeMark, Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant,
            Pageable pageable) {
        System.out.println(startDate);
        return controlDeRampaRepository.findByFilters(chassis, workingDay, serviceOrderId, tradeMark, level, startDate,
                endDate, startInstant, endInstant, pageable).map(controlDeRampaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<TradeMarkSelect> findGroupByTradeMark(String chassis, String workingDay, Long serviceOrderId,
            String tradeMark, Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant) {
        return controlDeRampaRepository.tradeMarkSelectByFilters(chassis, workingDay, serviceOrderId, tradeMark, level,
                startDate, endDate, startInstant, endInstant);
    }

    @Transactional(readOnly = true)
    public List<DateWorkingDaySelect> findGroupByDate(String chassis, String workingDay, Long serviceOrderId,
            String tradeMark, Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant) {
        return controlDeRampaRepository.dateSelectByFilters(chassis, workingDay, serviceOrderId, tradeMark, level,
                startDate, endDate, startInstant, endInstant);
    }

    @Transactional(readOnly = true)
    public List<LevelSelect> findGroupByLevel(String chassis, String workingDay, Long serviceOrderId, String tradeMark,
            Integer level, Date startDate, Date endDate, Instant startInstant, Instant endInstant) {
        return controlDeRampaRepository.levelSelectByFilters(chassis, workingDay, serviceOrderId, tradeMark, level,
                startDate, endDate, startInstant, endInstant);
    }
}
