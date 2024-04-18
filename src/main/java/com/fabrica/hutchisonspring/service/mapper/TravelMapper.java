package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Travel;
import com.fabrica.hutchisonspring.service.dto.TravelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ShipMapper.class })
public interface TravelMapper extends EntityMapper<TravelDTO, Travel> {

    @Mapping(source = "ship.id", target = "shipId")
    @Mapping(source = "ship.name", target = "shipName")
    TravelDTO toDto(Travel travel);

    @Mapping(source = "shipId", target = "ship")
    Travel toEntity(TravelDTO travelDTO);

    default Travel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Travel travel = new Travel();
        travel.setId(id);
        return travel;
    }
}
