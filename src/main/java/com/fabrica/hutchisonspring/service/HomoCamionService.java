package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.HomoCamion;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.HomoCamionRepository;
import com.fabrica.hutchisonspring.service.dto.HomoCamionDTO;
import com.fabrica.hutchisonspring.service.mapper.HomoCamionMapper;
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
public class HomoCamionService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "ruc", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "razon_social", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "placa", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "tolva", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "conductor", Types.VARCHAR, 0, 0)
    };

    private final HomoCamionMapper homoCamionMapper;
    private final HomoCamionRepository homoCamionRepository;
    private final BatchRepository<HomoCamion> batchRepository;

    private final FileManager fileManager;

    public HomoCamionService(HomoCamionMapper homoCamionMapper, HomoCamionRepository homoCamionRepository, BatchRepository<HomoCamion> batchRepository, FileManager fileManager) {
        this.homoCamionMapper = homoCamionMapper;
        this.homoCamionRepository = homoCamionRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<HomoCamion> homoCamiones = ExcelUtils.sheetToEntities(HomoCamion.class, file.getInputStream(), "HOMO_CAMION", true, true);
            if (homoCamiones == null || homoCamiones.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(homoCamiones);
            var errorStatus = !(Boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<HomoCamion>> items = (List<ValidatorItem<HomoCamion>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(HomoCamion.class, items, errorStatus);
            if (!errorStatus) {
                batchRepository.bulkSave(HomoCamion.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();

        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<HomoCamionDTO> getCamiones(Pageable pageable) {
        return homoCamionRepository.findAll(pageable).map(homoCamionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<HomoCamionDTO> getCamion(Long id) {
        return homoCamionRepository.findById(id).map(homoCamionMapper::toDto);
    }

    public HomoCamionDTO save(HomoCamionDTO homoCamionDTO) {
        HomoCamion homoCamion = homoCamionMapper.toEntity(homoCamionDTO);
        homoCamion = homoCamionRepository.save(homoCamion);
        return homoCamionMapper.toDto(homoCamion);
    }
}
