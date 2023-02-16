package com.saludtools.api.service;

import com.saludtools.api.entity.Prescription;
import com.saludtools.api.entity.PrescriptionMedicine;
import com.saludtools.api.model.PrescriptionRequest;

import java.util.List;
import java.util.Optional;


public interface PrescriptionService {
    Prescription createPrescription(PrescriptionRequest prescriptionRequest);
    Prescription updatePrescription(Long id, PrescriptionRequest prescriptionRequest);
    List<Prescription> getAllPrescription(Long patientId);
    Prescription getPrescriptedById(Long id);
    List<PrescriptionMedicine> getMedicamentByPrescripted(Long id);
}

