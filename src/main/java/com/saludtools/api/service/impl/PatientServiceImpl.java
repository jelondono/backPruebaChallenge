package com.saludtools.api.service.impl;

import com.saludtools.api.entity.Medicine;
import com.saludtools.api.entity.Patient;
import com.saludtools.api.repository.PatientRepository;
import com.saludtools.api.service.PatientService;
import com.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Patient existingPatient = getPatientById(id);
        existingPatient.setName(patient.getName());
        existingPatient.setLastname(patient.getLastname());
        existingPatient.setBirthdate(patient.getBirthdate());
        existingPatient.setGender(patient.getGender());
        return patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
    }

    @Override
    public Page<Patient> getAllPatients(Integer page, Integer size, String sortBy) {
        return patientRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }
}
