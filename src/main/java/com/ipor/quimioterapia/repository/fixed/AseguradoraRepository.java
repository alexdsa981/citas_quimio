package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AseguradoraRepository extends JpaRepository<Aseguradora, Long> {
    List<Aseguradora> findAllByOrderByNombreAsc();
    List<Aseguradora> findByIsActiveTrueOrderByNombreAsc();

}
