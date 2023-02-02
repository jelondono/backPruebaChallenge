package com.saludtools.api.service.impl;

import com.saludtools.api.entity.Medicine;
import com.saludtools.api.entity.Patient;
import com.saludtools.api.repository.MedicineRepository;
import com.saludtools.api.repository.PatientRepository;
import com.saludtools.api.repository.PrescriptionRepository;
import com.saludtools.api.service.MedicineService;
import com.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Medicine> createMedicine(List<Medicine> medicine) {
        return medicineRepository.saveAll(medicine);
    }

    @Override
    public List<Medicine> updateMedicine(List<Medicine> medicine) {

        List<Medicine> medicineUpdate = new ArrayList<>();
        for (Medicine medicament : medicine) {
            Medicine existingMedicine = getMedicineById(medicament.getId());
            BeanUtils.copyProperties(medicament, existingMedicine, "id");
            medicineUpdate.add(medicineRepository.save(existingMedicine));
        }
        return medicineUpdate;
    }

    @Override
    public void deleteMedicine(Long id) {
        Medicine existingMedicine = getMedicineById(id);
        medicineRepository.delete(existingMedicine);
    }

    @Override
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medicina", "id", id));
    }

    @Override
    public List<Medicine> getAllMedicines(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId));
        return medicineRepository.findByGenderAndMaxAgeAndNotIn(patient.getId());
    }
}
