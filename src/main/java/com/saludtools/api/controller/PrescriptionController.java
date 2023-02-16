package com.saludtools.api.controller;

import com.saludtools.api.entity.Prescription;
import com.saludtools.api.entity.PrescriptionMedicine;
import com.saludtools.api.model.ApiResponse;
import com.saludtools.api.model.PrescriptionRequest;
import com.saludtools.api.service.PrescriptionService;
import com.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;
    @GetMapping
    public ResponseEntity<ApiResponse> getPrescriptions(
            @RequestParam(value = "patientId", required = false) Long patientId) {

        List<Prescription> prescriptions = prescriptionService.getAllPrescription(patientId);
        if (prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("Prescripciones", "Prescripcion", "No existen Prescripciones");
        }
        return new ResponseEntity<>(new ApiResponse(200, "Prescripciones encontradas", prescriptions), HttpStatus.OK);
    }
    @GetMapping("/{idPrescription}")
    public ResponseEntity<ApiResponse> getMedicamentByPrescription(
            @PathVariable Long idPrescription) {

        List<PrescriptionMedicine> prescriptions = prescriptionService.getMedicamentByPrescripted(idPrescription);
        if (!prescriptions.isEmpty()) {
            throw new ResourceNotFoundException("Medicamentos", "Medicamentos", "No existen medicamentos asociados a esta prescripción");
        }
        return new ResponseEntity<>(new ApiResponse(200, "Medicamentos encontrados", prescriptions), HttpStatus.OK);
    }
    @PostMapping
    public ApiResponse createPrescription(@Valid @RequestBody PrescriptionRequest prescriptionRequest) {
        return new ApiResponse(HttpStatus.OK.value(), "Prescripcion creada con exito", prescriptionService.createPrescription(prescriptionRequest));

    }
    @PutMapping("/{id}")
    public ApiResponse updatePrescription(@PathVariable Long id, @Valid @RequestBody PrescriptionRequest prescriptionRequest) {
        Prescription prescription = prescriptionService.updatePrescription(id, prescriptionRequest);
        return new ApiResponse(200, "Prescripcion actualizada con exito", prescription);
    }
}


