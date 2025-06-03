package com.ipor.quimioterapia.model.other.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FiltroFechasDTO {
    private LocalDate desde;
    private LocalDate hasta;
}
