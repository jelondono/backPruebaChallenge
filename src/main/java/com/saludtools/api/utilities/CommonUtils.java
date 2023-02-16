package com.saludtools.api.utilities;

import com.saludtools.api.entity.Medicine;
import com.saludtools.api.entity.Patient;
import com.saludtools.api.entity.Prescription;
import com.saludtools.api.service.MedicineService;
import com.saludtools.api.service.PatientService;
import com.saludtools.api.service.PrescriptionService;
import com.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommonUtils {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicineService medicineService;

    public Prescription validatePrescriptionExists(Long id) {
        return prescriptionService.getPrescriptedById(id);
    }
    public Patient validatePatientExists(Long id) {
        return  patientService.getPatientById(id);
    }

    public Medicine validateMedicineExists(Long id) {
        return medicineService.getMedicineById(id);
    }
}