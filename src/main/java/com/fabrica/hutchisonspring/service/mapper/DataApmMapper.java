package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.DataApm;
import com.fabrica.hutchisonspring.service.dto.DataApmDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataApmMapper extends EntityMapper<DataApmDTO, DataApm> {

    default DataApm fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataApm dataApm = new DataApm();
        dataApm.setId(id);
        return dataApm;
    }
}
