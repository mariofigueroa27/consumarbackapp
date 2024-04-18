package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.ServiceOrder;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.ServiceOrderRepository;
import com.fabrica.hutchisonspring.service.dto.ServiceOrderDTO;
import com.fabrica.hutchisonspring.service.mapper.ServiceOrderMapper;
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
public class OrderService {

    private static final ColumnMetaData[] DATA_FORMAT = { new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "service_number", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "registered_at", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "Operacion", Types.VARCHAR, 0, 0) };

    private final BatchRepository<ServiceOrder> batchRepository;
    private final ServiceOrderRepository serviceOrderRepository;
    private final ServiceOrderMapper serviceOrderMapper;

    private final FileManager fileManager;

    public OrderService(BatchRepository<ServiceOrder> batchRepository, ServiceOrderRepository serviceOrderRepository,
            ServiceOrderMapper serviceOrderMapper, FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderMapper = serviceOrderMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<ServiceOrder> serviceOrders = ExcelUtils.sheetToEntities(ServiceOrder.class, file.getInputStream(),
                    "REGISTRO_SERVICIOS", true, false);
            if (serviceOrders == null || serviceOrders.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(serviceOrders);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<ServiceOrder>> items = (List<ValidatorItem<ServiceOrder>>) validated
                    .get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(ServiceOrder.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(ServiceOrder.class, DATA_FORMAT, false);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<ServiceOrderDTO> getOrders(Pageable pageable) {
        return serviceOrderRepository.findAll(pageable).map(serviceOrderMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ServiceOrderDTO> getOrder(Long id) {
        return serviceOrderRepository.findById(id).map(serviceOrderMapper::toDto);
    }

    public ServiceOrderDTO save(ServiceOrderDTO serviceOrderDTO) {
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(serviceOrderDTO);
        serviceOrder = serviceOrderRepository.save(serviceOrder);
        return serviceOrderMapper.toDto(serviceOrder);
    }
}
