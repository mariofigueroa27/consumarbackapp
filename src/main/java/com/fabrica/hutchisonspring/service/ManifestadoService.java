package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.utils.ExcelUtils;
import com.fabrica.hutchisonspring.domain.Manifestado;
import com.fabrica.hutchisonspring.repository.BatchRepository;
import com.fabrica.hutchisonspring.repository.ManifestadoRepository;
import com.fabrica.hutchisonspring.service.dto.ManifestadoDTO;
import com.fabrica.hutchisonspring.service.mapper.ManifestadoMapper;
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
public class ManifestadoService {

    private static final ColumnMetaData[] DATA_FORMAT = {
            new ColumnMetaData(1, "id", Types.BIGINT, 0, 0),
            new ColumnMetaData(2, "producto", Types.VARCHAR, 0, 0),
            new ColumnMetaData(3, "calidad", Types.VARCHAR, 0, 0),
            new ColumnMetaData(4, "manifestada", Types.INTEGER, 0, 0),
            new ColumnMetaData(5, "bodega", Types.BIGINT, 0, 0)
    };

    private final ManifestadoMapper manifestadoMapper;
    private final ManifestadoRepository manifestadoRepository;
    private final BatchRepository<Manifestado> batchRepository;

    private final FileManager fileManager;

    public ManifestadoService(ManifestadoMapper manifestadoMapper, ManifestadoRepository manifestadoRepository, BatchRepository<Manifestado> batchRepository, FileManager fileManager) {
        this.manifestadoMapper = manifestadoMapper;
        this.manifestadoRepository = manifestadoRepository;
        this.batchRepository = batchRepository;
        this.fileManager = fileManager;
    }

    public String saveFromFile(MultipartFile file) {
        try {
            List<Manifestado> manifestados = ExcelUtils.sheetToEntities(Manifestado.class, file.getInputStream(), "MANIFESTADO", true, true);
            if (manifestados == null || manifestados.isEmpty()) {
                return ExcelUtils.NO_FILE_PATH;
            }
            var validated = ExcelUtils.validateItems(manifestados);
            var hasErrors = (boolean) validated.get(ExcelUtils.HAS_ERRORS_KEY);
            List<ValidatorItem<Manifestado>> items = (List<ValidatorItem<Manifestado>>) validated.get(ExcelUtils.LIST_KEY);
            var resultFile = fileManager.toFlatFile(Manifestado.class, items, hasErrors);
            if (!hasErrors) {
                batchRepository.bulkSave(Manifestado.class, DATA_FORMAT, true);
            }
            return resultFile.getAbsolutePath();
        } catch (IOException | IntrospectionException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Page<ManifestadoDTO> getManifestados(Pageable pageable) {
        return manifestadoRepository.findAll(pageable).map(manifestadoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ManifestadoDTO> getManifestado(Long id) {
        return manifestadoRepository.findById(id).map(manifestadoMapper::toDto);
    }

    public ManifestadoDTO save(ManifestadoDTO manifestadoDTO) {
        Manifestado manifestado = manifestadoMapper.toEntity(manifestadoDTO);
        manifestado = manifestadoRepository.save(manifestado);
        return manifestadoMapper.toDto(manifestado);
    }
}
