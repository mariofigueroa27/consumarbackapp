package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.HomoCamion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomoCamionRepository extends JpaRepository<HomoCamion, Long> {
}
