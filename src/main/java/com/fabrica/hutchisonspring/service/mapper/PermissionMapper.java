package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Permission;
import com.fabrica.hutchisonspring.service.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, RoleMapper.class })
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "role.id", target = "roleId")
    PermissionDTO toDto(Permission permission);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "roleId", target = "role")
    Permission toEntity(PermissionDTO permissionDTO);
}
