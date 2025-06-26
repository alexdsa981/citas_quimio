package com.ipor.quimioterapia.restricciones;

import java.time.LocalDate;
import java.time.LocalTime;

public interface HorarioOcupadoDTO {
    Long getIdFichaPaciente();
    LocalDate getFecha();
    LocalTime getHoraProgramada();
    LocalTime getHoraFinProtocolo();
    String getCodigo();
    Long getIdCubiculo();
    Integer getDuracionMinutosProtocolo();
}
