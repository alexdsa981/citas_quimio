package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.Paciente;
import com.ipor.quimioterapia.model.fixed.Aseguradora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
