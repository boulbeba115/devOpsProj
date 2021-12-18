package com.iit.demo.mapper;


import com.iit.demo.domain.Medication;
import com.iit.demo.dto.MedicationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MedicationMapper {

    public static MedicationDTO medicationToMedcationDTO(Medication medication) {
        if (medication == null) {
            return null;
        }
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setId(medication.getId());
        medicationDTO.setName(medication.getName());
        medicationDTO.setCategoryId(medication.getCategory().getId());
        medicationDTO.setCategoryName(medication.getName());
        medicationDTO.setPrice(medication.getPrice());
        return medicationDTO;
    }

    public static Medication medicationDTOToMedication(MedicationDTO medicationDTO) {
        Medication medication = new Medication();
        medication.setId(medicationDTO.getId());
        medication.setName(medicationDTO.getName());
        medication.setPrice(medicationDTO.getPrice());
        return medication;
    }

    public static Collection<MedicationDTO> medicationsToMedicationDTOs(Collection<Medication> allergies) {
        List<MedicationDTO> allergiesDTO = new ArrayList<>();
        allergies.forEach(x -> {
            allergiesDTO.add(medicationToMedcationDTO(x));
        });
        return allergiesDTO;
    }
}
