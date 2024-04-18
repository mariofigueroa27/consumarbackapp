package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.CheckoutDetail;
import com.fabrica.hutchisonspring.service.dto.CheckoutDetailDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DispatchDetailMapper.class })
public interface CheckoutDetailMapper extends EntityMapper<CheckoutDetailDTO, CheckoutDetail> {

    @Mapping(source = "dispatchDetail.id", target = "dispatchDetailId")
    @Mapping(source = "dispatchDetail.bl", target = "bl")
    @Mapping(source = "dispatchDetail.vehicle.chassis", target = "vehicleChassis")
    @Mapping(source = "dispatchDetail.vehicle.tradeMark", target = "vehicleTradeMark")
    @Mapping(source = "dispatchDetail.sdo", target = "sdo")
    @Mapping(source = "dispatchDetail.jobApm", target = "jobApm")
    @Mapping(source = "dispatchDetail.remissionGuide", target = "remissionGuide")
    CheckoutDetailDTO toDto(CheckoutDetail checkoutDetail);

    @Mapping(source = "dispatchDetailId", target = "dispatchDetail")
    CheckoutDetail toEntity(CheckoutDetailDTO checkoutDetailDTO);
}
