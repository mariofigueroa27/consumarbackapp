package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Ship;
import com.fabrica.hutchisonspring.service.dto.ShipDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipMapper extends EntityMapper<ShipDTO, Ship> {

    default Ship fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ship ship = new Ship();
        ship.setId(id);
        return ship;
    }
}
