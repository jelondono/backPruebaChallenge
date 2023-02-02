package com.saludtools.api.repository;

import com.saludtools.api.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Query(value = "SELECT m.* " +
            "FROM medicines m " +
            "WHERE (m.singleGender = (SELECT p.gender_id FROM patient p WHERE p.id = :patientId) OR m.singleGender IS NULL) " +
            "AND (m.maxAge >= (SELECT DATEDIFF(CURDATE(), p.birthdate) / 365.25 FROM patient p WHERE p.id = :patientId) OR m.maxAge = 0) " +
            "AND m.ID NOT IN (SELECT pm.medicine_id " +
            "FROM prescription_medicines pm " +
            "INNER JOIN prescriptions pr ON pm.prescription_id = pr.id " +
            "WHERE pr.patient_id = :patientId " +
            "AND pr.prescription_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW())", nativeQuery = true)
    List<Medicine> findByGenderAndMaxAgeAndNotIn(@Param("patientId") Long patientId);


}
