package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {

    Page<Ship> findAllByOperation(String operation, Pageable pageable);
}
