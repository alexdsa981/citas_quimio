package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoPacienteRepository extends JpaRepository<TipoPaciente, Long> {
    List<TipoPaciente> findAllByOrderByNombreAsc();
    List<TipoPaciente> findByIsActiveTrueOrderByNombreAsc();
}
