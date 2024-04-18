package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.DatoEquipo;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.DatoEquipoRepository;
import com.fabrica.hutchisonspring.service.dto.DatoEquipoDTO;
import com.fabrica.hutchisonspring.service.mapper.DatoEquipoMapper;
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
public class DatoEquipoService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "equipo", Types.VARCHAR, 0, 0)
    };
    private final DatoEquipoMapper datoEquipoMapper;
    private final DatoEquipoRepository datoEquipoRepository;
    private final BatchRepository<DatoEquipo> batchRepository;

    private final FileManager fileManager;

    public DatoEquipoService(DatoEquipoMapper datoEquipoMapper, DatoEquipoRepository datoEquipoRepository, BatchRepository<DatoEquipo> batchRepository, FileManager fileManager) {
        this.datoEquipoMapper = datoEquipoMapper;
        this.datoEquipoRepository = datoEquipoRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<DatoEquipo> datoEquipos = ExcelUtils.sheetToEntities(DatoEquipo.class, file.getInputStream(), "DATOS_EQUIPOS", true, true);
            if (datoEquipos == null || datoEquipos.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(datoEquipos);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<DatoEquipo>> items = (List<ValidatorItem<DatoEquipo>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(DatoEquipo.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(DatoEquipo.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<DatoEquipoDTO> getEquipos(Pageable pageable) {
        return datoEquipoRepository.findAll(pageable).map(datoEquipoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DatoEquipoDTO> getEquipo(Long id) {
        return datoEquipoRepository.findById(id).map(datoEquipoMapper::toDto);
    }

    public DatoEquipoDTO save(DatoEquipoDTO datoEquipoDTO) {
        DatoEquipo datoEquipo = datoEquipoMapper.toEntity(datoEquipoDTO);
        datoEquipo = datoEquipoRepository.save(datoEquipo);
        return datoEquipoMapper.toDto(datoEquipo);
    }
}
