package com.ipor.quimioterapia.recursos.personal.enfermera;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnfermeraRepository extends JpaRepository<Enfermera, Long> {
    List<Enfermera> findAllByOrderByNombreAsc();
    List<Enfermera> findByIsActiveTrueOrderByNombreAsc();
}
