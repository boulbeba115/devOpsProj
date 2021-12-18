package com.iit.demo.web.rest;


import java.lang.Integer;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;

import com.iit.demo.dto.MedicationDTO;
import com.iit.demo.service.MedicationService;
import com.iit.demo.util.CalculatorUtil;
import com.iit.demo.util.RestPreconditions;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Medication.
 */
@RestController
@RequestMapping("/api/medications")
public class MedicationResource {
 
@Autowired
    private  MedicationService medicationService;
 Class<? extends MedicationDTO> entityClass;

    private final Logger log = LoggerFactory.getLogger(MedicationService.class);

   //public MedicationResource(MedicationService medicationService) {
    //    this.medicationService = medicationService;
   // }

    /**
     * POST /medications : Create a new medication.
     *
     * @param medicationDTO
     * @param bindingResult
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new medication, or with status 400 (Bad Request) if the payload is invalid
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws MethodArgumentNotValidException
     */
    @ApiOperation("This will add stuff")
    @PostMapping
    public ResponseEntity<MedicationDTO> createMedication(@Valid @RequestBody MedicationDTO medicationDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
        try {
            MedicationDTO med=entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        log.debug("REST request to save Medication : {}", medicationDTO);
        if (medicationDTO.getId() != null) {
            bindingResult.addError(new FieldError("MedicationDTO", "code", "POST method does not accept a medication  with code"));
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        MedicationDTO result = medicationService.save(medicationDTO);
        return ResponseEntity.created(new URI("/api/medications/" + result.getId())).body(result);
    }

    /**
     * PUT /medications : Updates an existing medication.
     *
     * @param id
     * @param medicationDTO the medication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * medication, or with status 400 (Bad Request) if the medication is not valid,
     * or with status 500 (Internal Server Error) if the medication couldn't be
     * updated
     * @throws MethodArgumentNotValidException
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicationDTO> updateMedication(@PathVariable Integer id, @Valid @RequestBody MedicationDTO medicationDTO) {
        log.debug("Request to update Medication: {}", id);
        medicationDTO.setId(id);
        MedicationDTO result = medicationService.update(medicationDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET /medications/{id} : get the "id" medication.
     *
     * @param id the id of the medication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body of
     * medication, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicationDTO> getMedication(@PathVariable Integer id) {
        log.debug("Request to get Medication: {}", id);
        MedicationDTO dto = medicationService.findOne(id);
        RestPreconditions.checkFound(dto, "medication NotFound");
        return ResponseEntity.ok().body(dto);
    }

    /**
     * GET /medications : get all the medications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medications
     * in body
     */
    @GetMapping("")
    public Collection<MedicationDTO> getAllMedications() {

        log.debug("Request to get all  Medications : {}");
        return medicationService.findAll();
    }

    @GetMapping("/hello")
    public String greetings(@RequestParam("username")String userName) {

        return "hello"+userName;
    }

    /**
     * DELETE /medications/{id} : delete the "id" medication.
     *
     * @param id the id of the medication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Integer id) {
        log.debug("Request to delete Medication: {}", id);
        medicationService.delete(id);
        return ResponseEntity.ok().build();
    }


    @ApiOperation("This will add stuff")
    @PostMapping("/searches")
    public Collection<MedicationDTO> searcheMedicationsByIds(@Valid @RequestBody List<Integer> ids) throws URISyntaxException, MethodArgumentNotValidException {




        return medicationService.searchs(ids);
    }

    @GetMapping("/sum")
    public int sum(@RequestParam int a, @RequestParam int b){


        return CalculatorUtil.sum(a, b);
    }

    @GetMapping("/test")
    public String  sum(@RequestParam String uname, @RequestParam String pwd){

        return uname+' '+pwd;
    }


}
