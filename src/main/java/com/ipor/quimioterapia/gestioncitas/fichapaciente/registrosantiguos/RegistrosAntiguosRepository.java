package com.ipor.quimioterapia.gestioncitas.fichapaciente.registrosantiguos;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface RegistrosAntiguosRepository extends JpaRepository<RegistrosAntiguos, Long> {
    @Query("SELECT r FROM RegistrosAntiguos r WHERE r.fecIngreso BETWEEN :desde AND :hasta ORDER BY r.fecIngreso ASC, r.horaInicio ASC")
    List<RegistrosAntiguos> buscarFichasEntreFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

}
