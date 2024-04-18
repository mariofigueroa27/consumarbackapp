package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.BalanzaDetalle;
import com.fabrica.hutchisonspring.service.dto.BalanzaDetalleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanzaDetalleMapper extends EntityMapper<BalanzaDetalleDTO, BalanzaDetalle> {

    default BalanzaDetalle fromId(Long id) {
        if (id == null) {
            return null;
        }
        BalanzaDetalle balanzaDetalle = new BalanzaDetalle();
        balanzaDetalle.setId(id);
        return balanzaDetalle;
    }
}
