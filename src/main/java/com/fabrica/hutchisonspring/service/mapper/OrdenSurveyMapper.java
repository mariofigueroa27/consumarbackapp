package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.OrdenSurvey;
import com.fabrica.hutchisonspring.service.dto.OrdenSurveyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdenSurveyMapper extends EntityMapper<OrdenSurveyDTO, OrdenSurvey> {

    default OrdenSurvey fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrdenSurvey ordenSurvey = new OrdenSurvey();
        ordenSurvey.setId(id);
        return ordenSurvey;
    }
}
