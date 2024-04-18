package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.RoroOperation;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.RoroOperationRepository;
import com.fabrica.hutchisonspring.repository.UpdateRoroOperationRepository;
import com.fabrica.hutchisonspring.service.dto.RoroOperationDTO;
import com.fabrica.hutchisonspring.service.mapper.RoroOperationMapper;
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

@Service
@Transactional
public class RoroOperationService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "puerto", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "terminal", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "fecha", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "muelle", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "cliente", Types.VARCHAR, 0, 0),
            new ColumnMetaData(7, "operacion", Types.VARCHAR, 0, 0),
            new ColumnMetaData(8, "bl", Types.VARCHAR, 0, 0),
            new ColumnMetaData(9, "mercaderia", Types.VARCHAR, 0, 0),
            new ColumnMetaData(10, "consignatario", Types.VARCHAR, 0, 0),
            new ColumnMetaData(11, "chassis", Types.VARCHAR, 0, 0),
            new ColumnMetaData(12, "service_order_id", Types.BIGINT, 0, 0),
            new ColumnMetaData(13, "tavel_id", Types.BIGINT, 0, 0),
            new ColumnMetaData(14, "vehicle_id", Types.BIGINT, 0, 0)
    };

    private final RoroOperationMapper roroOperationMapper;

    private final BatchRepository<RoroOperation> batchRepository;

    private final UpdateRoroOperationRepository updateRoroOperationRepository;

    private final RoroOperationRepository roroOperationRepository;

    private final FileManager fileManager;

    public RoroOperationService(RoroOperationMapper roroOperationMapper, BatchRepository<RoroOperation> batchRepository, UpdateRoroOperationRepository updateRoroOperationRepository, RoroOperationRepository roroOperationRepository, FileManager fileManager) {
        this.roroOperationMapper = roroOperationMapper;
        this.batchRepository = batchRepository;
        this.updateRoroOperationRepository = updateRoroOperationRepository;
        this.roroOperationRepository = roroOperationRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(Long travelId, Long orderId, MultipartFile file) {
        try {
            List<RoroOperation> operations = ExcelUtils.sheetToEntities(RoroOperation.class, file.getInputStream(), "OPERACION_RORO", true, false);
            if (operations == null || operations.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            operations.forEach(elm -> {
                elm.setTravelId(travelId);
                elm.setServiceOrderId(orderId);
            });
            var validated = ExcelUtils.validateItems(operations);
            boolean hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<RoroOperation>> items = (List<ValidatorItem<RoroOperation>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(RoroOperation.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(RoroOperation.class, DATA_FORMAT, false);
                associate();
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void associate() {
        updateRoroOperationRepository.updateTable();
    }

    @Transactional(readOnly = true)
    public Page<RoroOperationDTO> findOperations(Pageable pageable) {
        return roroOperationRepository.findAll(pageable).map(roroOperationMapper::toDto);
    }

    public void deleteOperationWithNoChassis() {
        roroOperationRepository.deleteAllWithNoChassis();
    }
}
