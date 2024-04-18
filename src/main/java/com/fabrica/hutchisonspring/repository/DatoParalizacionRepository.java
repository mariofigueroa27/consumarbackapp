package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.DatoParalizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatoParalizacionRepository extends JpaRepository<DatoParalizacion, Long> {
}
