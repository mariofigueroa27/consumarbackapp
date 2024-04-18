package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.Permission;
import com.fabrica.hutchisonspring.repository.PermissionRepository;
import com.fabrica.hutchisonspring.service.dto.PermissionDTO;
import com.fabrica.hutchisonspring.service.mapper.PermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class PermissionService {

    private final PermissionMapper permissionMapper;

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionMapper permissionMapper, PermissionRepository permissionRepository) {
        this.permissionMapper = permissionMapper;
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionDTO> saveMulti(Long id, List<PermissionDTO> permissionDTOS) {
        List<Permission> before = permissionRepository.findByUserId(id);
        List<Permission> after = permissionMapper.toEntity(permissionDTOS);
        permissionFilter(before, after);
        permissionRepository.deleteAll(before);
        after = permissionRepository.saveAll(after);
        return permissionMapper.toDto(after);
    }

    private void permissionFilter(List<Permission> before, List<Permission> after) {
        Iterator<Permission> bfi = before.iterator();
        Iterator<Permission> afi = after.iterator();
        while (bfi.hasNext()) {
            Permission bfe = bfi.next();
            while(afi.hasNext()) {
                Permission afe = afi.next();
                if (bfe.getRole().getId().equals(afe.getRole().getId())) {
                    bfi.remove();
                    afi.remove();
                }
            }
        }
    }
}
