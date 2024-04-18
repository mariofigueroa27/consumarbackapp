package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.DatoParalizacion;
import com.fabrica.hutchisonspring.service.dto.DatoParalizacionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DatoParalizacionMapper extends EntityMapper<DatoParalizacionDTO, DatoParalizacion> {
    default DatoParalizacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        DatoParalizacion datoParalizacion = new DatoParalizacion();
        datoParalizacion.setId(id);
        return datoParalizacion;
    }
}
