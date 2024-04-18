package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.ServiceOrder;
import com.fabrica.hutchisonspring.domain.Travel;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.utils.FileManager;
import com.fabrica.hutchisonspring.utils.vm.ColumnMetaData;
import com.fabrica.hutchisonspring.utils.vm.ValidatorItem;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.Vehicle;
import com.fabrica.hutchisonspring.repository.UserRepository;
import com.fabrica.hutchisonspring.repository.VehicleRepository;
import com.fabrica.hutchisonspring.security.SecurityUtils;
import com.fabrica.hutchisonspring.service.dto.VehicleDTO;
import com.fabrica.hutchisonspring.service.mapper.VehicleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.sql.Types;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "chassis", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "operation", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "trade_mark", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "detail", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "labelled_date", Types.VARCHAR, 0, 0),
            new ColumnMetaData(7, "registered_at", Types.VARCHAR, 0, 0),
            new ColumnMetaData(8, "travel_id", Types.BIGINT, 0, 0),
            new ColumnMetaData(9, "user_id", Types.BIGINT, 0, 0),
            new ColumnMetaData(10, "service_order_id", Types.BIGINT, 0, 0)
    };

    private final VehicleMapper vehicleMapper;
    private final BatchRepository<Vehicle> batchRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    private final FileManager fileManager;

    public VehicleService(VehicleMapper vehicleMapper, BatchRepository<Vehicle> batchRepository, VehicleRepository vehicleRepository, UserRepository userRepository, FileManager fileManager) {
        this.vehicleMapper = vehicleMapper;
        this.batchRepository = batchRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(Long id, Long orderId, MultipartFile file) {
        try {
            List<Vehicle> vehicles = ExcelUtils.sheetToEntities(Vehicle.class, file.getInputStream(), "REGISTRO_VEHICULOS", true, false);
            if (vehicles == null || vehicles.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            Travel travel = new Travel();
            travel.setId(id);
            ServiceOrder serviceOrder = new ServiceOrder();
            serviceOrder.setId(orderId);
            vehicles.forEach(vehicle -> {
                vehicle.setTravel(travel);
                vehicle.setServiceOrder(serviceOrder);
            });
            var validated = ExcelUtils.validateItems(vehicles);
            boolean hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Vehicle>> items = (List<ValidatorItem<Vehicle>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Vehicle.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Vehicle.class, DATA_FORMAT, false);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<VehicleDTO> getVehicles(Long travelId, Pageable pageable) {
        return vehicleRepository
                .findByTravelId(travelId, pageable)
                .map(vehicleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<VehicleDTO> getVehicles(Pageable pageable) {
        return vehicleRepository
                .findAll(pageable)
                .map(vehicleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<VehicleDTO> getVehicle(Long id) {
        return vehicleRepository.findById(id).map(vehicleMapper::toDto);
    }

    public VehicleDTO save(VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    public void updateLabels(List<Long> ids) {
        SecurityUtils.getCurrentLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            for (Long id : ids.stream().filter(Objects::nonNull).collect(Collectors.toList())) {
                vehicleRepository.findOneById(id).map(vehicle -> {
                    vehicle.setLabelledDate(Instant.now());
                    vehicle.setUser(user);
                    return vehicle;
                });
            }
        });
    }
}
