package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.ControlDeRampa;
import com.fabrica.hutchisonspring.service.dto.ControlDeRampaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControlDeRampaMapper extends EntityMapper<ControlDeRampaDTO, ControlDeRampa> {
    default ControlDeRampa fromId(Long id) {
        if (id == null) {
            return null;
        }
        ControlDeRampa controlDeRampa = new ControlDeRampa();
        controlDeRampa.setId(id);
        return controlDeRampa;
    }
}
