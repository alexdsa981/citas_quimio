package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCieRepository extends JpaRepository<DetalleCie, Long> {
    List<DetalleCie> findByFichaPacienteId(Long idFichaPaciente);
    void deleteByFichaPacienteIdAndCieIdNotIn(Long idFichaPaciente, List<Long> cieIds);
}
