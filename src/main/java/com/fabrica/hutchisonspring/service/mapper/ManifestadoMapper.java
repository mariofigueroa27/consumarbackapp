package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Manifestado;
import com.fabrica.hutchisonspring.service.dto.ManifestadoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManifestadoMapper extends EntityMapper<ManifestadoDTO, Manifestado> {
    default Manifestado fromId(Long id) {
        if (id == null) {
            return null;
        }
        Manifestado manifestado = new Manifestado();
        manifestado.setId(id);
        return manifestado;
    }
}
