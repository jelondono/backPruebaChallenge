package com.saludtools.api.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "medicines")
@Data
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debe enviar el nombre del medicamento")
    private String name;

    private int minAge;
    private int maxAge;

    @ManyToOne
    @JoinColumn(name = "singleGender", referencedColumnName = "id")
    private Gender singleGender;

}