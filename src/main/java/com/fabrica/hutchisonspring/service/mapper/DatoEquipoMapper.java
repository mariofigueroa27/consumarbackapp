package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.DatoEquipo;
import com.fabrica.hutchisonspring.service.dto.DatoEquipoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DatoEquipoMapper extends EntityMapper<DatoEquipoDTO, DatoEquipo> {
    default DatoEquipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatoEquipo datoEquipo = new DatoEquipo();
        datoEquipo.setId(id);
        return datoEquipo;
    }
}
