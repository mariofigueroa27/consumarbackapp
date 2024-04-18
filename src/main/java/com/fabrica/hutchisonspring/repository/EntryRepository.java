package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
}
