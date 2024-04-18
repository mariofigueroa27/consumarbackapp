package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.composite.PermissionComposite;
import com.fabrica.hutchisonspring.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, PermissionComposite> {
    List<Permission> findByUserId(Long id);
}
