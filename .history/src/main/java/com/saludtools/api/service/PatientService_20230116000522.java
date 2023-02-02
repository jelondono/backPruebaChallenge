package com.api.service;

import com.api.entity.Patient;
import org.springframework.data.domain.Page;

public interface PatientService {
    Patient createPatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);
    Page<Patient> getAllPatients(Integer page, Integer size, String sortBy);
}
