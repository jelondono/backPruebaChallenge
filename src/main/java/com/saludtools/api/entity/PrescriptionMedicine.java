package com.saludtools.api.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "prescription_medicines")
@Data
public class PrescriptionMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;


}
