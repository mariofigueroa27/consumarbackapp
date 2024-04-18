package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.repository.RoleRepository;
import com.fabrica.hutchisonspring.service.dto.RoleDTO;
import com.fabrica.hutchisonspring.service.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {

    private final RoleMapper roleMapper;

    private final RoleRepository roleRepository;

    public RoleService(RoleMapper roleMapper, RoleRepository roleRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }
}
