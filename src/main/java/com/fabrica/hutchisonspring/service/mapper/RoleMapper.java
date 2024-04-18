package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Role;
import com.fabrica.hutchisonspring.service.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    default Role fromId(Long id) {
        if (id == null) {
            return null;
        }
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
