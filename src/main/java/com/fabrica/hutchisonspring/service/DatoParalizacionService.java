package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.DatoParalizacion;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.DatoParalizacionRepository;
import com.fabrica.hutchisonspring.service.dto.DatoParalizacionDTO;
import com.fabrica.hutchisonspring.service.mapper.DatoParalizacionMapper;
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
public class DatoParalizacionService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "detalle", Types.VARCHAR, 0, 0),
            new ColumnMetaData(2, "responsable", Types.VARCHAR, 0, 0)
    };

    private final DatoParalizacionMapper datoParalizacionMapper;
    private final DatoParalizacionRepository datoParalizacionRepository;
    private final BatchRepository<DatoParalizacion> batchRepository;

    private final FileManager fileManager;

    public DatoParalizacionService(DatoParalizacionMapper datoParalizacionMapper, DatoParalizacionRepository datoParalizacionRepository, BatchRepository<DatoParalizacion> batchRepository, FileManager fileManager) {
        this.datoParalizacionMapper = datoParalizacionMapper;
        this.datoParalizacionRepository = datoParalizacionRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<DatoParalizacion> datoParalizaciones = ExcelUtils.sheetToEntities(DatoParalizacion.class, file.getInputStream(), "DATOS_PARALIZACIONES", true, true);
            if (datoParalizaciones == null || datoParalizaciones.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(datoParalizaciones);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<DatoParalizacion>> items = (List<ValidatorItem<DatoParalizacion>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(DatoParalizacion.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(DatoParalizacion.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<DatoParalizacionDTO> getParalizaciones(Pageable pageable) {
        return datoParalizacionRepository
                .findAll(pageable)
                .map(datoParalizacionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DatoParalizacionDTO> getParalizacion(Long id) {
        return datoParalizacionRepository.findById(id).map(datoParalizacionMapper::toDto);
    }

    public DatoParalizacionDTO save(DatoParalizacionDTO datoParalizacionDTO) {
        DatoParalizacion datoParalizacion = datoParalizacionMapper.toEntity(datoParalizacionDTO);
        datoParalizacion = datoParalizacionRepository.save(datoParalizacion);
        return datoParalizacionMapper.toDto(datoParalizacion);
    }
}
