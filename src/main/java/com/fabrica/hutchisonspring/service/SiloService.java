package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.Silo;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.SiloRepository;
import com.fabrica.hutchisonspring.service.dto.SiloDTO;
import com.fabrica.hutchisonspring.service.mapper.SiloMapper;
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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SiloService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "job", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "category", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "vessel", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "special_cg", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "cg_cond", Types.VARCHAR, 0, 0),
            new ColumnMetaData(7, "mt", Types.DOUBLE, 0, 0),
            new ColumnMetaData(8, "m3", Types.INTEGER, 0, 0),
            new ColumnMetaData(9, "qty", Types.INTEGER, 0, 0),
            new ColumnMetaData(10, "direction", Types.VARCHAR, 0, 0),
            new ColumnMetaData(11, "rc_cond", Types.VARCHAR, 0, 0),
            new ColumnMetaData(12, "job_type", Types.VARCHAR, 0, 0),
            new ColumnMetaData(13, "opr_mode", Types.VARCHAR, 0, 0),
            new ColumnMetaData(14, "truck", Types.VARCHAR, 0, 0),
            new ColumnMetaData(15, "gate_ticket", Types.VARCHAR, 0, 0),
            new ColumnMetaData(16, "location", Types.VARCHAR, 0, 0),
            new ColumnMetaData(17, "re_handle", Types.VARCHAR, 0, 0),
            new ColumnMetaData(18, "start_date", Types.VARCHAR, 0, 0),
            new ColumnMetaData(19, "end_date", Types.VARCHAR, 0, 0),
            new ColumnMetaData(20, "delivery", Types.VARCHAR, 0, 0),
            new ColumnMetaData(21, "shift", Types.VARCHAR, 0, 0),
            new ColumnMetaData(22, "hatch", Types.VARCHAR, 0, 0),
            new ColumnMetaData(23, "eq", Types.VARCHAR, 0, 0),
            new ColumnMetaData(24, "crane", Types.VARCHAR, 0, 0),
            new ColumnMetaData(25, "final_stat", Types.VARCHAR, 0, 0),
            new ColumnMetaData(26, "re_mark", Types.VARCHAR, 0, 0),
            new ColumnMetaData(27, "cargo", Types.VARCHAR, 0, 0)
    };

    private final SiloMapper siloMapper;
    private final SiloRepository siloRepository;
    private final BatchRepository<Silo> batchRepository;

    private final FileManager fileManager;

    public SiloService(SiloMapper siloMapper, SiloRepository siloRepository, BatchRepository<Silo> batchRepository, FileManager fileManager) {
        this.siloMapper = siloMapper;
        this.siloRepository = siloRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<Silo> silos = ExcelUtils.sheetToEntities(Silo.class, file.getInputStream(), "SILOS", true, false);
            if (silos == null || silos.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(silos);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Silo>> items = (List<ValidatorItem<Silo>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Silo.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Silo.class, DATA_FORMAT, false);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<SiloDTO> getSilos(Pageable pageable) {
        return siloRepository
                .findAll(pageable)
                .map(siloMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SiloDTO> getSilo(Long id) {
        return siloRepository.findById(id).map(siloMapper::toDto);
    }

    public SiloDTO save(SiloDTO siloDTO) {
        Silo silo = siloMapper.toEntity(siloDTO);
        silo = siloRepository.save(silo);
        return siloMapper.toDto(silo);
    }
}
