package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.VentaBalanzaBase;
import com.fabrica.hutchisonspring.service.dto.VentaBalanzaBaseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VentaBalanzaBaseMapper extends EntityMapper<VentaBalanzaBaseDTO, VentaBalanzaBase> {

    default VentaBalanzaBase fromId(Long id) {
        if (id == null) {
            return null;
        }
        VentaBalanzaBase ventaBalanzaBase = new VentaBalanzaBase();
        ventaBalanzaBase.setId(id);
        return ventaBalanzaBase;
    }
}
