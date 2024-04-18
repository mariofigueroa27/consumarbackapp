package com.fabrica.hutchisonspring.repository;

import java.util.Optional;

import com.fabrica.hutchisonspring.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Page<Vehicle> findByTravelId(Long travelId, Pageable pageable);

    Optional<Vehicle> findOneById(Long id);
}
