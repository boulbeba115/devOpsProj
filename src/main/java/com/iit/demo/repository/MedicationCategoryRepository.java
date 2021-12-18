package com.iit.demo.repository;

import com.iit.demo.domain.Medication;
import com.iit.demo.domain.MedicationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Spring Data JPA repository for the Allergie entity.
 */
@Repository
public interface MedicationCategoryRepository extends JpaRepository<MedicationCategory, Integer> {
}

