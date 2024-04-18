package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.DataRecepcionado;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.DataRecepcionadoRepository;
import com.fabrica.hutchisonspring.service.dto.DataRecepcionadoDTO;
import com.fabrica.hutchisonspring.service.mapper.DataRecepcionadoMapper;
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
public class DataRecepcionadoService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "fecha", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "jornada", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "lado", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "codigo_despacho", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "capacidad", Types.DOUBLE, 0, 0),
            new ColumnMetaData(7, "recepcionado", Types.DOUBLE, 0, 0),
            new ColumnMetaData(8, "nave", Types.VARCHAR, 0, 0),
    };

    private final BatchRepository<DataRecepcionado> batchRepository;
    private final DataRecepcionadoRepository dataRecepcionadoRepository;
    private final DataRecepcionadoMapper dataRecepcionadoMapper;

    private final FileManager fileManager;

    public DataRecepcionadoService(BatchRepository<DataRecepcionado> batchRepository, DataRecepcionadoRepository dataRecepcionadoRepository, DataRecepcionadoMapper dataRecepcionadoMapper, FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.dataRecepcionadoRepository = dataRecepcionadoRepository;
        this.dataRecepcionadoMapper = dataRecepcionadoMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<DataRecepcionado> dataRecepcionados = ExcelUtils.sheetToEntities(DataRecepcionado.class, file.getInputStream(), "DATA_RECEPCIONADO", true, true);
            if (dataRecepcionados == null || dataRecepcionados.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(dataRecepcionados);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<DataRecepcionado>> items = (List<ValidatorItem<DataRecepcionado>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(DataRecepcionado.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(DataRecepcionado.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public DataRecepcionadoDTO save(DataRecepcionadoDTO dataRecepcionadoDTO) {
        DataRecepcionado dataRecepcionado = dataRecepcionadoMapper.toEntity(dataRecepcionadoDTO);
        dataRecepcionado = dataRecepcionadoRepository.save(dataRecepcionado);
        return dataRecepcionadoMapper.toDto(dataRecepcionado);
    }

    @Transactional(readOnly = true)
    public Page<DataRecepcionadoDTO> getDataRecepcionados(Pageable pageable) {
        return dataRecepcionadoRepository.findAll(pageable).map(dataRecepcionadoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DataRecepcionadoDTO> getDataRecepcionado(Long id) {
        return dataRecepcionadoRepository.findById(id).map(dataRecepcionadoMapper::toDto);
    }
}
