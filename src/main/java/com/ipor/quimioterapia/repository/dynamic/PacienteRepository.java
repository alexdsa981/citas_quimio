package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.Paciente;
import com.ipor.quimioterapia.model.fixed.Aseguradora;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByHistoria(String historia);
    Optional<Paciente> findByTipoDocIdentidadIdAndNumDocIdentidad(Long idTipoDoc, String numDocIdentidad);
}
