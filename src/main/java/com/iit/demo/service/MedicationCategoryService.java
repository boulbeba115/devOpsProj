package com.iit.demo.service;


import com.iit.demo.domain.MedicationCategory;
import com.iit.demo.repository.MedicationCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.iit.demo.util.Preconditions.checkBusinessLogique;

/**
 * Service Implementation for managing MedicationCategory.
 */
@Service
@Transactional
public class MedicationCategoryService {

    private final Logger log = LoggerFactory.getLogger(MedicationCategoryService.class);

    private final MedicationCategoryRepository medicationRepository;
 

    public MedicationCategoryService(MedicationCategoryRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }








    /**
     * Get one medication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(
            readOnly = true
    )
    public MedicationCategory findMedicationCategory(Integer id) {
        log.debug("Request to get MedicationCategory: {}", id);
        MedicationCategory medication = medicationRepository.getOne(id);
        return medication;
    }

   /* *//**
     * Get all the medications.
     *
     * @return the the list of entities
     *//*
    @Transactional(
            readOnly = true
    )
    public Collection<MedicationCategoryDTO> findAll() {
        log.debug("Request to get All MedicationCategorys");
        Collection<MedicationCategory> result = medicationRepository.findAll();
        return MedicationCategoryMapper.medicationsToMedicationCategoryDTOs(result);
    }*/

    /**
     * Delete medication by id.
     *
     * @param id the id of the entity
     */
    public void delete(Integer id) {
        log.debug("Request to delete MedicationCategory: {}", id);
        checkBusinessLogique(medicationRepository.existsById(id),"medication.does.not.exist",id.toString(),"hall","abbb" ); //==> "14;hall;abbb"
        medicationRepository.deleteById(id);
    }


}
