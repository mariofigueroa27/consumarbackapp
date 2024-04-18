package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Trace;
import com.fabrica.hutchisonspring.service.dto.TraceDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { VehicleMapper.class })
public interface TraceMapper extends EntityMapper<TraceDTO, Trace> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.chassis", target = "vehicleChassis")
    @Mapping(source = "vehicle.tradeMark", target = "vehicleTradeMark")
    TraceDTO toDto(Trace trace);

    @Mapping(source = "vehicleId", target = "vehicle.id")
    Trace toEntity(TraceDTO traceDTO);
}
