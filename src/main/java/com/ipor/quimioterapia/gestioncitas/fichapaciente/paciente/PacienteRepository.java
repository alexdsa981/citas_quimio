package com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByIdPaciente(Long idPaciente);

}
