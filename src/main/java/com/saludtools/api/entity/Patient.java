package com.saludtools.api.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "patient")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede ser vacio")
    @NotNull
    @Size(max = 100)
    private String name;

    @NotBlank(message = "El apellido no puede ser vacio")
    @NotNull
    @Size(max = 100)
    private String lastname;

    @NotNull(message = "Debe enviar una fecha de cumpleaños")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

}
