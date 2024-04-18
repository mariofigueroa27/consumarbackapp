package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.DispatchDetail;
import com.fabrica.hutchisonspring.service.dto.DispatchDetailDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { VehicleMapper.class })
public interface DispatchDetailMapper extends EntityMapper<DispatchDetailDTO, DispatchDetail> {
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.chassis", target = "vehicleChassis")
    @Mapping(source = "vehicle.tradeMark", target = "vehicleTradeMark")
    DispatchDetailDTO toDto(DispatchDetail dispatchDetail);

    @Mapping(source = "vehicleId", target = "vehicle")
    DispatchDetail toEntity(DispatchDetailDTO dispatchDetailDTO);

    default DispatchDetail fromId(Long id) {
        if (id == null) {
            return null;
        }
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setId(id);
        return dispatchDetail;
    }
}
