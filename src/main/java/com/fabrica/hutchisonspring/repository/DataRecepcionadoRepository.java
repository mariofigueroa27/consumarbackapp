package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.DataRecepcionado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRecepcionadoRepository extends JpaRepository<DataRecepcionado, Long> {
}
