package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.OrdenSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenSurveyRepository extends JpaRepository<OrdenSurvey, Long> {
}
