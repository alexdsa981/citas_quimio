package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.Cita;
import com.ipor.quimioterapia.model.fixed.Aseguradora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {

}
