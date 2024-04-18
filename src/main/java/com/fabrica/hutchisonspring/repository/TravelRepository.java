package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    Page<Travel> findByShipId(Long shipId, Pageable pageable);
}
