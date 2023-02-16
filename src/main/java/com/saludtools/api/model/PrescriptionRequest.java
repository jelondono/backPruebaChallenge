package com.saludtools.api.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionRequest {
    
    @NotNull
    private Long patientId;

    @NotNull
    private LocalDate prescriptionDate;

    @NotNull
    private List<Long> medicineIds;

    public PrescriptionRequest(Long patientId, LocalDate prescriptionDate, List<Long> medicineIds) {
        this.patientId = patientId;
        this.prescriptionDate = prescriptionDate;
        this.medicineIds = medicineIds;
    }

    public PrescriptionRequest() {

    }
}
