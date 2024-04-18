package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.Ship;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.Travel;
import com.fabrica.hutchisonspring.repository.TravelRepository;
import com.fabrica.hutchisonspring.service.dto.TravelDTO;
import com.fabrica.hutchisonspring.service.mapper.TravelMapper;
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
public class TravelService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "travel_number", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "start_at", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "end_at", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "registered_at", Types.VARCHAR, 0, 0),
            new ColumnMetaData(6, "ship_id", Types.BIGINT, 0, 0)
    };

    private final BatchRepository<Travel> batchRepository;
    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;

    private final FileManager fileManager;

    public TravelService(BatchRepository<Travel> batchRepository, TravelRepository travelRepository, TravelMapper travelMapper, FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.travelRepository = travelRepository;
        this.travelMapper = travelMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(Long id, MultipartFile file) {
        try {
            List<Travel> travels = ExcelUtils.sheetToEntities(Travel.class, file.getInputStream(), "REGISTRO_VIAJES", true, false);
            if (travels == null || travels.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            Ship ship = new Ship();
            ship.setId(id);
            travels.forEach(travel -> travel.setShip(ship));
            var validated = ExcelUtils.validateItems(travels);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Travel>> items = (List<ValidatorItem<Travel>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Travel.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Travel.class, DATA_FORMAT, false);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<TravelDTO> getTravels(Long shipId, Pageable pageable) {
        return travelRepository.findByShipId(shipId, pageable).map(travelMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TravelDTO> getTravels(Pageable pageable) {
        return travelRepository.findAll(pageable).map(travelMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TravelDTO> getTravel(Long id) {
        return travelRepository.findById(id).map(travelMapper::toDto);
    }

    public TravelDTO save(TravelDTO travelDTO) {
        Travel travel = travelMapper.toEntity(travelDTO);
        travel = travelRepository.save(travel);
        return travelMapper.toDto(travel);
    }
}
