package com.fabrica.hutchisonspring.service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import com.fabrica.hutchisonspring.domain.RoroOperation;
import com.fabrica.hutchisonspring.domain.Sdo;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.RoroOperationRepository;
import com.fabrica.hutchisonspring.repository.SdoRepository;
import com.fabrica.hutchisonspring.service.dto.SdoDTO;
import com.fabrica.hutchisonspring.service.mapper.SdoMapper;
import com.fabrica.hutchisonspring.service.mapper.ServiceOrderMapper;
import com.fabrica.hutchisonspring.service.mapper.TravelMapper;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.utils.FileManager;
import com.fabrica.hutchisonspring.utils.vm.ColumnMetaData;
import com.fabrica.hutchisonspring.utils.vm.ValidatorItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class SdoService {
    private static final ColumnMetaData[] DATA_FORMAT = { new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "Master_BL", Types.VARCHAR, 0, 0), new ColumnMetaData(3, "SDO", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "Release_QTY", Types.INTEGER, 0, 0),
            new ColumnMetaData(5, "Chas_QTY", Types.INTEGER, 0, 0),
            new ColumnMetaData(6, "Fec_Registro", Types.VARCHAR, 0, 0),
            new ColumnMetaData(7, "travel_id", Types.BIGINT, 0, 0),
            new ColumnMetaData(8, "service_order_id", Types.BIGINT, 0, 0), };

    private final SdoRepository sdoRepository;
    private final RoroOperationRepository roroOperationRepository;
    private final BatchRepository<Sdo> batchRepository;

    private final SdoMapper sdoMapper;
    private final TravelMapper travelMapper;
    private final ServiceOrderMapper serviceOrderMapper;

    private final FileManager fileManager;

    public SdoService(SdoRepository sdoRepository, SdoMapper sdoMapper, TravelMapper travelMapper,
            ServiceOrderMapper serviceOrderMapper, FileManager fileManager, BatchRepository<Sdo> batchRepository,
            RoroOperationRepository roroOperationRepository) {
        this.sdoRepository = sdoRepository;
        this.sdoMapper = sdoMapper;
        this.travelMapper = travelMapper;
        this.serviceOrderMapper = serviceOrderMapper;
        this.fileManager = fileManager;
        this.batchRepository = batchRepository;
        this.roroOperationRepository = roroOperationRepository;
    }

    public String saveFromFile(Long travelId, Long orderId, MultipartFile file) {
        try {
            List<Sdo> sdos = ExcelUtils.sheetToEntities(Sdo.class, file.getInputStream(), "SDO", true, false);
            if (sdos == null || sdos.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            sdos.forEach(elm -> {
                elm.setTravel(travelMapper.fromId(travelId));
                elm.setOrder(serviceOrderMapper.fromId(orderId));
            });
            var validated = ExcelUtils.validateItems(sdos);
            boolean hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Sdo>> items = (List<ValidatorItem<Sdo>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Sdo.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Sdo.class, DATA_FORMAT, false);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<SdoDTO> getSdos(Pageable pageable) {
        return sdoRepository.findAll(pageable).map(sdoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SdoDTO> getSdo(Long id) {
        return sdoRepository.findById(id).map(sdoMapper::toDto);
    }

    public SdoDTO save(SdoDTO serviceOrderDTO) {
        Sdo sdo = sdoMapper.toEntity(serviceOrderDTO);
        validateBL(sdo.getBl());
        sdo = sdoRepository.save(sdo);
        return sdoMapper.toDto(sdo);
    }

    private void validateBL(String bl) {
        Optional<RoroOperation> roroOperation = roroOperationRepository.findByBl(bl);
        if (roroOperation.isEmpty()) {
            throw new RuntimeException("invalid BL");
        }
    }
}
