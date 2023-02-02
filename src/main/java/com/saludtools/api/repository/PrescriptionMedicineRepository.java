package com.saludtools.api.repository;

import com.saludtools.api.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
    void deleteByPrescriptionId(Long prescriptionId);
}
