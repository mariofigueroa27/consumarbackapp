package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.RoroOperation;
import com.fabrica.hutchisonspring.service.dto.RoroOperationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoroOperationMapper extends EntityMapper<RoroOperationDTO, RoroOperation> {

    default RoroOperation fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoroOperation roroOperation = new RoroOperation();
        roroOperation.setId(id);
        return roroOperation;
    }
}
