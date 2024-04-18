package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Sdo;
import com.fabrica.hutchisonspring.service.dto.SdoDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { TravelMapper.class, ServiceOrderMapper.class })
public interface SdoMapper extends EntityMapper<SdoDTO, Sdo> {

    @Mapping(source = "travel.id", target = "travelId")
    @Mapping(source = "travel.travelNumber", target = "travelNumber")
    @Mapping(source = "travel.ship.id", target = "shipId")
    @Mapping(source = "travel.ship.name", target = "shipName")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.serviceNumber", target = "orderNumber")
    SdoDTO toDto(Sdo sdo);

    @Mapping(source = "travelId", target = "travel")
    @Mapping(source = "orderId", target = "order")
    Sdo toEntity(SdoDTO sdoDTO);
}
