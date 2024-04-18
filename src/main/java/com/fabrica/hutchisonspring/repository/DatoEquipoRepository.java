package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.DatoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatoEquipoRepository extends JpaRepository<DatoEquipo, Long> {
}
