package com.saludtools.api.service.impl;

import com.saludtools.api.entity.Gender;
import com.saludtools.api.entity.Medicine;
import com.saludtools.api.entity.Patient;
import com.saludtools.api.entity.Prescription;
import com.saludtools.api.model.PrescriptionRequest;
import com.saludtools.api.repository.PrescriptionRepository;
import com.saludtools.api.service.PrescriptionService;
import com.saludtools.api.utilities.AppConstants;
import com.saludtools.api.utilities.CommonUtils;
import com.saludtools.api.utilities.exceptions.InvalidRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PrescriptionServiceImplTest {
    @Mock
    private PrescriptionRepository prescriptionRepository;
    @Mock
    private CommonUtils commonUtils;
    @InjectMocks
    private PrescriptionService prescriptionService = new PrescriptionServiceImpl();

    private PrescriptionRequest prescriptionRequest;
    private Patient patient;
    private List<Medicine> medicines;

    @Before
    public void setUp() {
        prescriptionRequest = new PrescriptionRequest();
        Gender gender = new Gender();
        gender.setId(1L);
        patient = new Patient();
        patient.setId(1L);
        patient.setName("John");
        patient.setLastname("Doe");
        patient.setBirthdate(java.sql.Date.valueOf(LocalDate.of(2000, 1, 1)));
        patient.setGender(gender);
        medicines = new ArrayList<>();
        Medicine medicine1 = new Medicine();
        medicine1.setId(1L);
        medicine1.setMinAge(18);
        medicine1.setMaxAge(100);
        medicine1.setSingleGender(null);
        medicines.add(medicine1);
        Medicine medicine2 = new Medicine();
        medicine2.setId(2L);
        medicine2.setMinAge(18);
        medicine2.setMaxAge(100);
        medicine2.setSingleGender(gender);
        medicines.add(medicine2);

        prescriptionRequest.setPatientId(patient.getId());
        prescriptionRequest.setPrescriptionDate(LocalDate.now());
        List<Long> medicineIds = new ArrayList<>();
        medicineIds.add(medicine1.getId());
        medicineIds.add(medicine2.getId());
        prescriptionRequest.setMedicineIds(medicineIds);

    }

    @Test
    public void createPrescription_InvalidRequestException_genderNotMatch() {
        Gender otroGenero = new Gender();
        otroGenero.setId(2L);
        when(commonUtils.validatePatientExists(patient.getId())).thenReturn(patient);
        when(commonUtils.validateMedicineExists(medicines.get(0).getId())).thenReturn(medicines.get(0));
        when(commonUtils.validateMedicineExists(medicines.get(1).getId())).thenReturn(medicines.get(1));
        patient.setGender(otroGenero);
        try {
            prescriptionService.createPrescription(prescriptionRequest);
        } catch (InvalidRequestException e) {
            assertEquals("Uno o mas medicamentos no son aptos para el género del paciente", e.getMessage());
        }
        verify(prescriptionRepository, times(0)).save(any());
    }
    @Test
    public void updatePrescription_Success() {
        when(commonUtils.validatePrescriptionExists(1L)).thenReturn(new Prescription());
        when(commonUtils.validatePatientExists(patient.getId())).thenReturn(patient);
        when(commonUtils.validateMedicineExists(medicines.get(0).getId())).thenReturn(medicines.get(0));
        when(commonUtils.validateMedicineExists(medicines.get(1).getId())).thenReturn(medicines.get(1));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(new Prescription());

        Prescription updatedPrescription = prescriptionService.updatePrescription(1L, prescriptionRequest);

        verify(prescriptionRepository, times(1)).save(any());
        assertNotNull(updatedPrescription);
    }
    @Test
    public void getAllPrescription_Success() {
        List<Prescription> expectedPrescriptions = new ArrayList<>();
        expectedPrescriptions.add(new Prescription());
        expectedPrescriptions.add(new Prescription());
        when(prescriptionRepository.findByPatientId(patient.getId())).thenReturn(expectedPrescriptions);

        List<Prescription> prescriptions = prescriptionService.getAllPrescription(patient.getId());

        verify(prescriptionRepository, times(1)).findByPatientId(patient.getId());
        assertNotNull(prescriptions);
        assertEquals(expectedPrescriptions.size(), prescriptions.size());
    }
    @Test
    public void createPrescription_PrescriptionLimitExceeded() {
        when(commonUtils.validatePatientExists(patient.getId())).thenReturn(patient);
        when(commonUtils.validateMedicineExists(medicines.get(0).getId())).thenReturn(medicines.get(0));
        when(commonUtils.validateMedicineExists(medicines.get(1).getId())).thenReturn(medicines.get(1));
        when(prescriptionRepository.findByPatientIdAndPrescriptionDateBetween(patient.getId(), LocalDate.now().withDayOfMonth(1), LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())))
                .thenReturn(getPrescriptions(AppConstants.MAX_MONTHLY_PRESCRIPTIONS - 1));

        try {
            prescriptionService.createPrescription(prescriptionRequest);
        } catch (InvalidRequestException e) {
            assertEquals("No se pueden prescribir más de " + AppConstants.MAX_MONTHLY_PRESCRIPTIONS + " medicamentos por mes al mismo paciente", e.getMessage());
        }
    }

    private List<Prescription> getPrescriptions(int numberOfPrescriptions) {
        List<Prescription> prescriptions = new ArrayList<>();
        for (int i = 0; i < numberOfPrescriptions; i++) {
            Prescription prescription = new Prescription();
            prescription.setId((long) i);
            prescription.setMedicines(medicines);
            prescription.setPatient(patient);
            prescription.setPrescriptionDate(LocalDate.now());
            prescriptions.add(new Prescription());
        }
        return prescriptions;
    }
}