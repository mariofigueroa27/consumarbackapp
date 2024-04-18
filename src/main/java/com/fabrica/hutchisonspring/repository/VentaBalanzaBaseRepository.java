package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.VentaBalanzaBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaBalanzaBaseRepository extends JpaRepository<VentaBalanzaBase, Long> {
}
