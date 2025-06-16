package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByIdPaciente(Long idPaciente);

}
