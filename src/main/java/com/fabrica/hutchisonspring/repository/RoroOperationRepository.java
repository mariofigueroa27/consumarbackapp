package com.fabrica.hutchisonspring.repository;

import java.util.Optional;

import com.fabrica.hutchisonspring.domain.RoroOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoroOperationRepository extends JpaRepository<RoroOperation, Long> {

    @Modifying
    @Transactional
    @Query("delete from RoroOperation r where r.vehicleId is null")
    void deleteAllWithNoChassis();

    Optional<RoroOperation> findByBl(String bl);
}
