package com.iit.demo.service;


import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import java.util.List;

import com.iit.demo.dto.MedicationDTO;
import com.iit.demo.mapper.MedicationMapper;
import com.iit.demo.repository.MedicationRepository;
import com.iit.demo.domain.Medication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.iit.demo.util.Preconditions.checkBusinessLogique;

/**
 * Service Implementation for managing Medication.
 */
@Service
@Transactional
public class MedicationService {

    private final Logger log = LoggerFactory.getLogger(MedicationService.class);

    private final MedicationRepository medicationRepository;
    private  final  MedicationCategoryService medicationCategoryService;

    public MedicationService(MedicationRepository medicationRepository, MedicationCategoryService medicationCategoryService) {
        this.medicationRepository = medicationRepository;
        this.medicationCategoryService = medicationCategoryService;
    }

    /**
     * Save a medicationDTO.
     *
     * @param medicationDTO the dto to be saved
     * @return the persisted entity
     */
    public MedicationDTO save(MedicationDTO medicationDTO) {
        log.debug("Request to save Medication: {}", medicationDTO);
        Medication medication = MedicationMapper.medicationDTOToMedication(medicationDTO);
        medication.setCategory(medicationCategoryService.findMedicationCategory(medicationDTO.getId()));
        medication = medicationRepository.save(medication);
        MedicationDTO resultDTO = MedicationMapper.medicationToMedcationDTO(medication);
        return resultDTO;
    }


    /**
     * @param medicationDTO
     * @return
     */
    public MedicationDTO update(MedicationDTO medicationDTO) {
        log.debug("Request to update Medication: {} ", medicationDTO);//INFO
        Medication inBase = medicationRepository.getOne(medicationDTO.getId());
//        Preconditions.checkArgument(inBase != null, "medication.NotFound");
        Medication medication = MedicationMapper.medicationDTOToMedication(medicationDTO);
        medication.setCategory(medicationCategoryService.findMedicationCategory(medicationDTO.getId()));
        medication = medicationRepository.save(medication);
        MedicationDTO resultDTO = MedicationMapper.medicationToMedcationDTO(medication);
        return resultDTO;
    }

    /**
     * Get one medicationDTO by id.
     *
     * @param id the id of the entity
     * @return the entity DTO
     */
    @Transactional
    public MedicationDTO findOne(Integer id) {
        log.debug("Request to get Medication: {}", id);
        Medication medication = medicationRepository.getOne(id);

        medication.setName("changeed");
        MedicationDTO dto = MedicationMapper.medicationToMedcationDTO(medication);
        return dto;
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
    public Medication findMedication(Integer id) {
        log.debug("Request to get Medication: {}", id);
        Medication medication = medicationRepository.getOne(id);
        return medication;
    }

    /**
     * Get all the medications.
     *
     * @return the the list of entities
     */
    @Transactional(
            readOnly = true
    )
    public Collection<MedicationDTO> findAll() {
        log.debug("Request to get All Medications");
        Collection<Medication> result = medicationRepository.findAll();
        return MedicationMapper.medicationsToMedicationDTOs(result);
    }

    /**
     * Get all the medications.
     *
     * @return the the list of entities
     */
    @Transactional(
            readOnly = true
    )
    public Collection<MedicationDTO> searchs(List<Integer> ids) {
        log.debug("Request to get All Medications");
        Collection<Medication> result = medicationRepository.findAllById(ids);
        return MedicationMapper.medicationsToMedicationDTOs(result);
    }

    /**
     * Delete medication by id.
     *
     * @param id the id of the entity
     */
    public void delete(Integer id) {
        log.debug("Request to delete Medication: {}", id);
        checkBusinessLogique(medicationRepository.existsById(id),"medication.does.not.exist",id.toString() ); //==> "14;hall;abbb"
        medicationRepository.deleteById(id);
    }


}
