package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Silo;
import com.fabrica.hutchisonspring.service.dto.SiloDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiloMapper extends EntityMapper<SiloDTO, Silo> {

    default Silo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Silo silo = new Silo();
        silo.setId(id);
        return silo;
    }
}
