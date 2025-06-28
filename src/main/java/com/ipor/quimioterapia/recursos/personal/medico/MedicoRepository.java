package com.ipor.quimioterapia.recursos.personal.medico;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findAllByOrderByNombreAsc();
    List<Medico> findByIsActiveTrueOrderByNombreAsc();
}
