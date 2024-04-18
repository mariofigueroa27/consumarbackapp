package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Vehicle;
import com.fabrica.hutchisonspring.service.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { TravelMapper.class, ServiceOrderMapper.class, UserMapper.class })
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "travel.id", target = "travelId")
    @Mapping(source = "travel.travelNumber", target = "travelNumber")
    @Mapping(source = "serviceOrder.id", target = "serviceOrderId")
    @Mapping(source = "serviceOrder.serviceNumber", target = "serviceOrderNumber")
    @Mapping(source = "travel.ship.id", target = "shipId")
    @Mapping(source = "travel.ship.name", target = "shipName")
    @Mapping(source = "user.id", target = "userId")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(source = "travelId", target = "travel")
    @Mapping(source = "serviceOrderId", target = "serviceOrder")
    @Mapping(source = "userId", target = "user")
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
