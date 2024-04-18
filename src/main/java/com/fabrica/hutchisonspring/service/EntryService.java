package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.Entry;
import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.EntryRepository;
import com.fabrica.hutchisonspring.service.dto.EntryDTO;
import com.fabrica.hutchisonspring.service.mapper.EntryMapper;
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
public class EntryService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "shipCode", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "weight", Types.DOUBLE, 0, 0),
            new ColumnMetaData(4, "plate", Types.VARCHAR, 0, 0),
            new ColumnMetaData(5, "employee_id", Types.BIGINT, 0, 0)
    };

    private final BatchRepository<Entry> batchRepository;
    private final EntryRepository entryRepository;
    private final EntryMapper entryMapper;

    private final FileManager fileManager;

    public EntryService(BatchRepository<Entry> batchRepository, EntryRepository entryRepository, EntryMapper entryMapper, FileManager fileManager) {
        this.batchRepository = batchRepository;
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<Entry> entries = ExcelUtils.sheetToEntities(Entry.class, file.getInputStream(), "DATA_ENTRY", true, true);
            if (entries == null || entries.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(entries);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Entry>> items = (List<ValidatorItem<Entry>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Entry.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Entry.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<EntryDTO> getEntries(Pageable pageable) {
        return entryRepository.findAll(pageable).map(entryMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EntryDTO> getEntry(Long id) {
        return entryRepository.findById(id).map(entryMapper::toDto);
    }

    public EntryDTO save(EntryDTO entryDTO) {
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }
}
