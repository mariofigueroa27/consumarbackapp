package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.ServiceOrder;
import com.fabrica.hutchisonspring.service.dto.ServiceOrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceOrderMapper extends EntityMapper<ServiceOrderDTO, ServiceOrder> {
    default ServiceOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(id);
        return serviceOrder;
    }
}
