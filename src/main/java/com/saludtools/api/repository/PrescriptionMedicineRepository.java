package com.saludtools.api.repository;

import com.saludtools.api.entity.Prescription;
import com.saludtools.api.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
    void deleteByPrescriptionId(Long prescriptionId);
    @Query("SELECT p FROM PrescriptionMedicine p WHERE p.prescription = :idPrescription")
    List<PrescriptionMedicine> findByPrescriptiontId(@Param("idPrescription") Long idPrescription);
}
