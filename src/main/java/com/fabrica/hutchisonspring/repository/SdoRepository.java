package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Sdo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdoRepository extends JpaRepository<Sdo, Long> {

}
