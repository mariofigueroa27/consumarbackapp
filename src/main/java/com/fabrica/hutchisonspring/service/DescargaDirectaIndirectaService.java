package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.DescargaDirectaIndirecta;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.DescargaDirectaIndirectaRepository;
import com.fabrica.hutchisonspring.service.dto.DescargaDirectaIndirectaDTO;
import com.fabrica.hutchisonspring.service.mapper.DescargaDirectaIndirectaMapper;
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
public class DescargaDirectaIndirectaService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "fecha", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "modalidad", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "jornada_inicio", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "jornada_fin", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "descargo", Types.DOUBLE, 0, 0),
            new ColumnMetaData(7, "bodega", Types.BIGINT, 0, 0)
    };

    private final DescargaDirectaIndirectaMapper descargaDirectaIndirectaMapper;
    private final DescargaDirectaIndirectaRepository descargaDirectaIndirectaRepository;
    private final BatchRepository<DescargaDirectaIndirecta> batchRepository;

    private final FileManager fileManager;

    public DescargaDirectaIndirectaService(DescargaDirectaIndirectaMapper descargaDirectaIndirectaMapper, DescargaDirectaIndirectaRepository descargaDirectaIndirectaRepository, BatchRepository<DescargaDirectaIndirecta> batchRepository, FileManager fileManager) {
        this.descargaDirectaIndirectaMapper = descargaDirectaIndirectaMapper;
        this.descargaDirectaIndirectaRepository = descargaDirectaIndirectaRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<DescargaDirectaIndirecta> descargaDirectaIndirectas = ExcelUtils.sheetToEntities(DescargaDirectaIndirecta.class, file.getInputStream(), "DESCARGA_DIRECT_INDIREC", true, true);
            if (descargaDirectaIndirectas == null || descargaDirectaIndirectas.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(descargaDirectaIndirectas);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<DescargaDirectaIndirecta>> items = (List<ValidatorItem<DescargaDirectaIndirecta>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(DescargaDirectaIndirecta.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(DescargaDirectaIndirecta.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<DescargaDirectaIndirectaDTO> getDescargas(Pageable pageable) {
        return descargaDirectaIndirectaRepository.findAll(pageable).map(descargaDirectaIndirectaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DescargaDirectaIndirectaDTO> getDescarga(Long id) {
        return descargaDirectaIndirectaRepository.findById(id).map(descargaDirectaIndirectaMapper::toDto);
    }

    public DescargaDirectaIndirectaDTO save(DescargaDirectaIndirectaDTO descargaDirectaIndirectaDTO) {
        DescargaDirectaIndirecta descargaDirectaIndirecta = descargaDirectaIndirectaMapper.toEntity(descargaDirectaIndirectaDTO);
        descargaDirectaIndirecta = descargaDirectaIndirectaRepository.save(descargaDirectaIndirecta);
        return descargaDirectaIndirectaMapper.toDto(descargaDirectaIndirecta);
    }
}
