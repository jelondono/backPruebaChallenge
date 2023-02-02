package com.saludtools.saludtools.api.controller;

import com.saludtools.saludtools.api.entity.Patient;
import com.saludtools.saludtools.api.model.ApiResponse;
import com.saludtools.saludtools.api.service.PatientService;
import com.saludtools.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            HttpStatus status) {
        Page<Patient> patients = patientService.getAllPatients(page, size, sortBy);
        if (patients.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NO_CONTENT.value(), "No existen pacientes",null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(200, "Pacientes encontrados", patients), HttpStatus.OK);
    }


    @PostMapping
    public ApiResponse createPatient(@Valid @RequestBody Patient patient) {
        Patient newPatient = patientService.createPatient(patient);
        return new ApiResponse(200, "Paciente creado con exito", newPatient);
    }

    @PutMapping("/{id}")
    public ApiResponse updatePatient(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return new ApiResponse(200, "Patient updated successfully", updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return new ApiResponse(HttpStatus.CONTINUE.value(), "Paciente eliminado satisfactoriamente", null);
        } catch (ResourceNotFoundException ex) {
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage(), null);
        }
    }

}

