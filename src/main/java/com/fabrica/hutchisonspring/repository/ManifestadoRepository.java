package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Manifestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManifestadoRepository extends JpaRepository<Manifestado, Long> {
}
