package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.fixed.TipoDocIdentidad;
import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoDocIdentidadRepository extends JpaRepository<TipoDocIdentidad, Long> {
    List<TipoDocIdentidad> findAllByOrderByNombreAsc();
    List<TipoDocIdentidad> findByIsActiveTrueOrderByNombreAsc();
}
