package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.DataRecepcionado;
import com.fabrica.hutchisonspring.service.dto.DataRecepcionadoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataRecepcionadoMapper extends EntityMapper<DataRecepcionadoDTO, DataRecepcionado> {

    default DataRecepcionado fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataRecepcionado dataRecepcionado = new DataRecepcionado();
        dataRecepcionado.setId(id);
        return dataRecepcionado;
    }
}
