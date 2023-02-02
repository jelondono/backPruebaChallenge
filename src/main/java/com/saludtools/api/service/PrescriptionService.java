package com.saludtools.api.service;

import com.saludtools.api.entity.Prescription;
import com.saludtools.api.model.PrescriptionRequest;
import org.springframework.data.domain.Page;

import java.util.List;


public interface PrescriptionService {
    Prescription createPrescription(PrescriptionRequest prescriptionRequest);
    Prescription updatePrescription(Long id, PrescriptionRequest prescriptionRequest);
    List<Prescription> getAllPrescription(Long patientId);
    Prescription getPrescriptedById(Long id);
}

