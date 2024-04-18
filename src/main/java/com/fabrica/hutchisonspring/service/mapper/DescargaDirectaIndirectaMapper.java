package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.DescargaDirectaIndirecta;
import com.fabrica.hutchisonspring.service.dto.DescargaDirectaIndirectaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DescargaDirectaIndirectaMapper extends EntityMapper<DescargaDirectaIndirectaDTO, DescargaDirectaIndirecta> {

    default DescargaDirectaIndirecta fromId(Long id) {
        if (id == null) {
            return null;
        }
        DescargaDirectaIndirecta descargaDirectaIndirecta = new DescargaDirectaIndirecta();
        descargaDirectaIndirecta.setId(id);
        return descargaDirectaIndirecta;
    }
}
