package com.iit.demo.repository;

import java.lang.Boolean;
import java.lang.Integer;
import java.util.Collection;

import com.iit.demo.domain.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Allergie entity.
 */
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
}

