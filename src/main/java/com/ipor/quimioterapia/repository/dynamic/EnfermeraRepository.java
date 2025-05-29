package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.Enfermera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnfermeraRepository extends JpaRepository<Enfermera, Long> {
    List<Enfermera> findAllByOrderByNombreAsc();
    List<Enfermera> findByIsActiveTrueOrderByNombreAsc();
}
