package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.HomoCamion;
import com.fabrica.hutchisonspring.service.dto.HomoCamionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HomoCamionMapper extends EntityMapper<HomoCamionDTO, HomoCamion> {
    default HomoCamion fromId(Long id) {
        if (id == null) {
            return null;
        }
        HomoCamion homoCamion = new HomoCamion();
        homoCamion.setId(id);
        return homoCamion;
    }
}
