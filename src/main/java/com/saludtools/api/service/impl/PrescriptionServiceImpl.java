package com.saludtools.api.service.impl;

import com.saludtools.api.entity.Medicine;
import com.saludtools.api.entity.Patient;
import com.saludtools.api.entity.Prescription;
import com.saludtools.api.entity.PrescriptionMedicine;
import com.saludtools.api.model.PrescriptionRequest;
import com.saludtools.api.repository.PrescriptionMedicineRepository;
import com.saludtools.api.repository.PrescriptionRepository;
import com.saludtools.api.service.PrescriptionService;
import com.saludtools.api.utilities.AppConstants;
import com.saludtools.api.utilities.CommonUtils;
import com.saludtools.api.utilities.exceptions.InvalidRequestException;
import com.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private CommonUtils commonUtils;
    @Autowired
    private PrescriptionMedicineRepository prescriptionMedicineRepository;
    @Override
    public Prescription createPrescription(PrescriptionRequest prescriptionRequest) {
        Patient patient = commonUtils.validatePatientExists(prescriptionRequest.getPatientId());

        List<Medicine> medicines = prescriptionRequest.getMedicineIds().stream()
                .map(id -> commonUtils.validateMedicineExists(id))
                .collect(Collectors.toList());

        validateGender(patient, medicines);
        validatePrescriptionLimits(patient, medicines, prescriptionRequest.getPrescriptionDate().atStartOfDay());
        validateAge(patient, medicines);

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setPrescriptionDate(prescriptionRequest.getPrescriptionDate());
        prescription.setMedicines(medicines);

        return prescriptionRepository.save(prescription);
    }
    private void validateGender(Patient patient, List<Medicine> medicines) {
        if (medicines.stream().anyMatch(medicine -> {
            if (medicine.getSingleGender() != null)
                return !medicine.getSingleGender().equals(patient.getGender());
            else return false;
        })) {
            throw new InvalidRequestException("Uno o mas medicamentos no son aptos para el género del paciente");
        }
    }
    private void validatePrescriptionLimits(Patient patient, List<Medicine> medicines, LocalDateTime prescriptionDate) {
        LocalDate start = prescriptionDate.toLocalDate().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        List<Prescription> prescriptions = prescriptionRepository.findByPatientIdAndPrescriptionDateBetween(patient.getId(), start, end);

        int totalMedicines = 0;
        if (prescriptions != null) {
            totalMedicines = prescriptions.stream().mapToInt(p -> p.getMedicines() != null ? p.getMedicines().size() : 0).sum();
        }
        if (medicines != null) {
            totalMedicines += medicines.size();
        }

        if (totalMedicines > AppConstants.MAX_MONTHLY_PRESCRIPTIONS) {
            throw new InvalidRequestException("No se pueden prescribir más de " + AppConstants.MAX_MONTHLY_PRESCRIPTIONS + " medicamentos por mes al mismo paciente");
        }
        if (prescriptions != null && medicines != null) {
            long matchCount = prescriptions.stream()
                    .filter(p -> p.getMedicines() != null && medicines != null && p.getMedicines().containsAll(medicines))
                    .count();
            if (matchCount > 0) {
                throw new InvalidRequestException("No se puede prescribir el mismo medicamento dentro del mismo mes al mismo paciente");
            }
        }
    }
    private void validateAge(Patient patient, List<Medicine> medicines) {
        LocalDate birthdate = new java.sql.Date(patient.getBirthdate().getTime()).toLocalDate();
        LocalDate now = LocalDate.now();
        int age = Period.between(birthdate, now).getYears();
        for (Medicine medicine : medicines) {
            int minAge = medicine.getMinAge();
            int maxAge = medicine.getMaxAge();
            if (minAge > 0 && maxAge == 0) {
                if (age < minAge) {
                    throw new InvalidRequestException("El paciente es menor a la edad permitida para consumir este medicamento");
                }
            } else {
                if (age < minAge || age > maxAge) {
                    throw new InvalidRequestException("El paciente no se encuentra en el rango de edad para consumir este medicamento");
                }
            }
        }
    }
    @Override
    public Prescription updatePrescription(Long idPrescription, PrescriptionRequest prescriptionRequest) {
        Prescription prescription = commonUtils.validatePrescriptionExists(idPrescription);
        Patient patient = commonUtils.validatePatientExists(prescriptionRequest.getPatientId());
        List<Medicine> medicines = prescriptionRequest.getMedicineIds().stream()
                .map(id -> commonUtils.validateMedicineExists(id))
                .collect(Collectors.toList());

        validateGender(patient, medicines);
        validatePrescriptionLimits(patient, medicines, prescriptionRequest.getPrescriptionDate().atStartOfDay());
        validateAge(patient, medicines);

        // Actualizar la información de la prescripcion
        prescription.setPrescriptionDate(prescriptionRequest.getPrescriptionDate());
        prescription.setPatient(patient);
        prescription.setMedicines(medicines);
        return prescriptionRepository.save(prescription);
    }
    @Override
    public List<Prescription> getAllPrescription(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);

    }
    @Override
    public Prescription getPrescriptedById(Long id) {
        return prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescripción", "id", id));
    }
    @Override
    public List<PrescriptionMedicine> getMedicamentByPrescripted(Long idPrescription) {
        return prescriptionMedicineRepository.findByPrescriptiontId(idPrescription);
    }

}
