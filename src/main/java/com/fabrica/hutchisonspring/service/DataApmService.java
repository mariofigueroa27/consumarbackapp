package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.DataApm;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.DataApmRepository;
import com.fabrica.hutchisonspring.service.dto.DataApmDTO;
import com.fabrica.hutchisonspring.service.mapper.DataApmMapper;
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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DataApmService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "mt", Types.DOUBLE, 0, 0),
            new ColumnMetaData(3, "gate_ticket", Types.VARCHAR, 0, 0)
    };

    private final BatchRepository<DataApm> batchRepository;
    private final DataApmRepository dataApmRepository;
    private final DataApmMapper dataApmMapper;

    private final FileManager fileManager;

    public DataApmService(BatchRepository<DataApm> batchRepository, DataApmRepository dataApmRepository, DataApmMapper dataApmMapper, FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.dataApmRepository = dataApmRepository;
        this.dataApmMapper = dataApmMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<DataApm> datasApm = ExcelUtils.sheetToEntities(DataApm.class, file.getInputStream(), "DATA_APM", true, true);
            if (datasApm == null || datasApm.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(datasApm);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<DataApm>> items = (List<ValidatorItem<DataApm>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(DataApm.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(DataApm.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public DataApmDTO save(DataApmDTO dataApmDTO) {
        DataApm dataApm = dataApmMapper.toEntity(dataApmDTO);
        dataApm = dataApmRepository.save(dataApm);
        return dataApmMapper.toDto(dataApm);
    }

    @Transactional(readOnly = true)
    public Page<DataApmDTO> getDatasApm(Pageable pageable) {
        return dataApmRepository.findAll(pageable).map(dataApmMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DataApmDTO> getDataApm(Long id) {
        return dataApmRepository.findById(id).map(dataApmMapper::toDto);
    }
}
