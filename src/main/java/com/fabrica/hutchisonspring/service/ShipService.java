package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.Ship;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.ShipRepository;
import com.fabrica.hutchisonspring.service.dto.ShipDTO;
import com.fabrica.hutchisonspring.service.mapper.ShipMapper;
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
public class ShipService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "name", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "registered_at", Types.VARCHAR, 0, 0)
    };

    private final BatchRepository<Ship> batchRepository;
    private final ShipRepository shipRepository;
    private final ShipMapper shipMapper;

    private final FileManager fileManager;

    public ShipService(BatchRepository<Ship> batchRepository, ShipRepository shipRepository, ShipMapper shipMapper, FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.shipRepository = shipRepository;
        this.shipMapper = shipMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<Ship> ships = ExcelUtils.sheetToEntities(Ship.class, file.getInputStream(), "REGISTRO_NAVES", true, false);
            if (ships == null || ships.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(ships);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Ship>> items = (List<ValidatorItem<Ship>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Ship.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Ship.class, DATA_FORMAT, false);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<ShipDTO> getShips(String operation, Pageable pageable) {
        return shipRepository.findAllByOperation(operation, pageable).map(shipMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ShipDTO> getShips(Pageable pageable) {
        return shipRepository.findAll(pageable).map(shipMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ShipDTO> getShip(Long id) {
        return shipRepository.findById(id).map(shipMapper::toDto);
    }

    public ShipDTO save(ShipDTO shipDTO) {
        Ship ship = shipMapper.toEntity(shipDTO);
        ship = shipRepository.save(ship);
        return shipMapper.toDto(ship);
    }
}
