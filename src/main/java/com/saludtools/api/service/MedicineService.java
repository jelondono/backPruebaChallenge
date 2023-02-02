package com.saludtools.api.service;

import com.saludtools.api.entity.Medicine;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MedicineService {
    List<Medicine> createMedicine(List<Medicine> medicine);
    List<Medicine> updateMedicine(List<Medicine> medicine);
    void deleteMedicine(Long id);
    Medicine getMedicineById(Long id);
    List<Medicine> getAllMedicines(Long patientId);
}
