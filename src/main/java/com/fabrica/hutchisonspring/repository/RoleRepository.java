package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
