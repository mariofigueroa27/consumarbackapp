package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.DataApm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataApmRepository extends JpaRepository<DataApm, Long> {
}
