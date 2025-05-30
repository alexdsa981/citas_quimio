package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.TipoEntrada;
import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoEntradaRepository extends JpaRepository<TipoEntrada, Long> {
    List<TipoEntrada> findAllByOrderByNombreAsc();
    List<TipoEntrada> findByIsActiveTrueOrderByNombreAsc();
}
