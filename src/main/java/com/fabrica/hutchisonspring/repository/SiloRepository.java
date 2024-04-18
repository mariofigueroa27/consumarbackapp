package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Silo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiloRepository extends JpaRepository<Silo, Long> {
}
