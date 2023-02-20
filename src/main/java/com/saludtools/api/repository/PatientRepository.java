package com.saludtools.api.repository;

import com.saludtools.api.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findByNameContainingIgnoreCaseAndGenderId(String nombre, Long generoId, Pageable pageable);
    Page<Patient> findByNameContainingIgnoreCase(String nombre, Pageable pageable);
    Page<Patient> findByGenderId(Long generoId,Pageable pageable);
}
