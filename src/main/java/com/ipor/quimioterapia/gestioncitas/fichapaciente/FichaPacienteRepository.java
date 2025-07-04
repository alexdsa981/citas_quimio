package com.ipor.quimioterapia.gestioncitas.fichapaciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FichaPacienteRepository extends JpaRepository<FichaPaciente, Long> {
    @Query("SELECT f FROM FichaPaciente f WHERE f.cita.fecha = :fecha ORDER BY f.cita.horaProgramada ASC")
    List<FichaPaciente> buscarFichasPorFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT f FROM FichaPaciente f WHERE f.cita.fecha BETWEEN :desde AND :hasta ORDER BY f.cita.fecha ASC, f.cita.horaProgramada ASC")
    List<FichaPaciente> buscarFichasEntreFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    @Query("""
    SELECT f FROM FichaPaciente f
    WHERE f.cita.paciente.id = :pacienteId
      AND f.id <> :excluirId
    ORDER BY f.cita.fecha DESC
    """)
    List<FichaPaciente> findByCitaPacienteIdAndIdNotOrderByCitaFechaDesc(Long pacienteId, Long excluirId);

}
