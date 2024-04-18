package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.OrdenSurvey;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.OrdenSurveyRepository;
import com.fabrica.hutchisonspring.service.dto.OrdenSurveyDTO;
import com.fabrica.hutchisonspring.service.mapper.OrdenSurveyMapper;
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
public class OrdenSurveyService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "nave", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "ticket", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "cliente", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "muelle", Types.BIGINT, 0, 0)
    };

    private final OrdenSurveyMapper ordenSurveyMapper;
    private final OrdenSurveyRepository ordenSurveyRepository;
    private final BatchRepository<OrdenSurvey> batchRepository;

    private final FileManager fileManager;

    public OrdenSurveyService(OrdenSurveyMapper ordenSurveyMapper, OrdenSurveyRepository ordenSurveyRepository, BatchRepository<OrdenSurvey> batchRepository, FileManager fileManager) {
        this.ordenSurveyMapper = ordenSurveyMapper;
        this.ordenSurveyRepository = ordenSurveyRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<OrdenSurvey> ordenSurveys = ExcelUtils.sheetToEntities(OrdenSurvey.class, file.getInputStream(), "CREACION_DE_SERVICIO", true, true);
            if (ordenSurveys == null || ordenSurveys.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(ordenSurveys);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<OrdenSurvey>> items = (List<ValidatorItem<OrdenSurvey>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(OrdenSurvey.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(OrdenSurvey.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<OrdenSurveyDTO> getSurveys(Pageable pageable) {
        return ordenSurveyRepository
                .findAll(pageable)
                .map(ordenSurveyMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<OrdenSurveyDTO> getSurvey(Long id) {
        return ordenSurveyRepository.findById(id).map(ordenSurveyMapper::toDto);
    }

    public OrdenSurveyDTO save(OrdenSurveyDTO ordenSurveyDTO) {
        OrdenSurvey ordenSurvey = ordenSurveyMapper.toEntity(ordenSurveyDTO);
        ordenSurvey = ordenSurveyRepository.save(ordenSurvey);
        return ordenSurveyMapper.toDto(ordenSurvey);
    }
}
