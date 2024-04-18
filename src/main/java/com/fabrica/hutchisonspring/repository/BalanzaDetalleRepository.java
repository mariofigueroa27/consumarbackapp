package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.BalanzaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanzaDetalleRepository extends JpaRepository<BalanzaDetalle, Long> {
}
