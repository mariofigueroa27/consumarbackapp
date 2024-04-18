package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.VentaBalanzaBase;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.VentaBalanzaBaseRepository;
import com.fabrica.hutchisonspring.service.dto.VentaBalanzaBaseDTO;
import com.fabrica.hutchisonspring.service.mapper.VentaBalanzaBaseMapper;
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
public class VentaBalanzaBaseService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "producto", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "cliente", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "factura", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "cantidad_autorizada", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "viajes_autorizados", Types.BIGINT, 0, 0)
    };

    private final BatchRepository<VentaBalanzaBase> batchRepository;
    private final VentaBalanzaBaseRepository ventaBalanzaBaseRepository;
    private final VentaBalanzaBaseMapper ventaBalanzaBaseMapper;

    private final FileManager fileManager;

    public VentaBalanzaBaseService(BatchRepository<VentaBalanzaBase> batchRepository, VentaBalanzaBaseRepository ventaBalanzaBaseRepository, VentaBalanzaBaseMapper ventaBalanzaBaseMapper, FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.ventaBalanzaBaseRepository = ventaBalanzaBaseRepository;
        this.ventaBalanzaBaseMapper = ventaBalanzaBaseMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<VentaBalanzaBase> ventasBalanzaBase = ExcelUtils.sheetToEntities(VentaBalanzaBase.class, file.getInputStream(), "VENTAS_BALANZA_BASE", true, true);
            if (ventasBalanzaBase == null || ventasBalanzaBase.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(ventasBalanzaBase);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<VentaBalanzaBase>> items = (List<ValidatorItem<VentaBalanzaBase>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(VentaBalanzaBase.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(VentaBalanzaBase.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<VentaBalanzaBaseDTO> getVentas(Pageable pageable) {
        return ventaBalanzaBaseRepository
                .findAll(pageable)
                .map(ventaBalanzaBaseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<VentaBalanzaBaseDTO> getVenta(Long id) {
        return ventaBalanzaBaseRepository.findById(id).map(ventaBalanzaBaseMapper::toDto);
    }

    public VentaBalanzaBaseDTO save(VentaBalanzaBaseDTO ventaBalanzaBaseDTO) {
        VentaBalanzaBase ventaBalanzaBase = ventaBalanzaBaseMapper.toEntity(ventaBalanzaBaseDTO);
        ventaBalanzaBase = ventaBalanzaBaseRepository.save(ventaBalanzaBase);
        return ventaBalanzaBaseMapper.toDto(ventaBalanzaBase);
    }
}
