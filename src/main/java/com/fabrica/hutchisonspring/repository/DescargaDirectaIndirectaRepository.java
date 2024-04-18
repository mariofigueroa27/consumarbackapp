package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.DescargaDirectaIndirecta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescargaDirectaIndirectaRepository extends JpaRepository<DescargaDirectaIndirecta, Long> {
}
