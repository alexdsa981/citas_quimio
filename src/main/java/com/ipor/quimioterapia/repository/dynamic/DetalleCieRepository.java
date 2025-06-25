package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.DetalleCie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCieRepository extends JpaRepository<DetalleCie, Long> {
    List<DetalleCie> findByFichaPacienteId(Long idFichaPaciente);
    void deleteByFichaPacienteIdAndCieIdNotIn(Long idFichaPaciente, List<Long> cieIds);
}
