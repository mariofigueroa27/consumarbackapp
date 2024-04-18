package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.BalanzaDetalle;
import com.fabrica.hutchisonspring.repository.BalanzaDetalleRepository;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.service.dto.BalanzaDetalleDTO;
import com.fabrica.hutchisonspring.service.mapper.BalanzaDetalleMapper;
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
public class BalanzaDetalleService {

    private static final ColumnMetaData[] DATA_FORMAT = { new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "ticket", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "hora_ingreso_puerto", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "hora_salida_puerto", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "placa", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "empresa_transporte", Types.VARCHAR, 0, 0),
            new ColumnMetaData(7, "peso_bruto_puerto", Types.DOUBLE, 0, 0),
            new ColumnMetaData(8, "peso_neto_puerto", Types.DOUBLE, 0, 0),
            new ColumnMetaData(9, "tara_puerto", Types.VARCHAR, 0, 0),
            new ColumnMetaData(10, "balanza_entrada", Types.BIGINT, 0, 0),
            new ColumnMetaData(11, "balanza_salida", Types.DOUBLE, 0, 0),
            new ColumnMetaData(12, "cliente", Types.BIGINT, 0, 0),
            new ColumnMetaData(13, "factura", Types.VARCHAR, 0, 0) };

    private final BalanzaDetalleMapper balanzaDetalleMapper;
    private final BalanzaDetalleRepository balanzaDetalleRepository;
    private final BatchRepository<BalanzaDetalle> batchRepository;

    private final FileManager fileManager;

    public BalanzaDetalleService(BalanzaDetalleMapper balanzaDetalleMapper,
            BalanzaDetalleRepository balanzaDetalleRepository, BatchRepository<BalanzaDetalle> batchRepository,
            FileManager fileManager) {
        this.balanzaDetalleMapper = balanzaDetalleMapper;
        this.balanzaDetalleRepository = balanzaDetalleRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<BalanzaDetalle> detalles = ExcelUtils.sheetToEntities(BalanzaDetalle.class, file.getInputStream(),
                    "BALANZA_DETALLE", true, true);
            if (detalles == null || detalles.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(detalles);
            boolean hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<BalanzaDetalle>> items = (List<ValidatorItem<BalanzaDetalle>>) validated
                    .get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(BalanzaDetalle.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(BalanzaDetalle.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<BalanzaDetalleDTO> getDetalles(Pageable pageable) {
        return balanzaDetalleRepository.findAll(pageable).map(balanzaDetalleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<BalanzaDetalleDTO> getDetalle(Long id) {
        return balanzaDetalleRepository.findById(id).map(balanzaDetalleMapper::toDto);
    }

    public BalanzaDetalleDTO save(BalanzaDetalleDTO balanzaDetalleDTO) {
        BalanzaDetalle balanzaDetalle = balanzaDetalleMapper.toEntity(balanzaDetalleDTO);
        balanzaDetalle = balanzaDetalleRepository.save(balanzaDetalle);
        return balanzaDetalleMapper.toDto(balanzaDetalle);
    }
}
