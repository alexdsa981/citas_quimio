package com.ipor.quimioterapia.model.fixed;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cie {
    @Id
    private Long id;
    private String codigo;
    private String descripcion;
}
