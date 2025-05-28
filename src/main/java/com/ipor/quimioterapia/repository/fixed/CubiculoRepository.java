package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Cubiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CubiculoRepository extends JpaRepository<Cubiculo, Long> {
    List<Cubiculo> findAllByOrderByCodigoAsc();
    List<Cubiculo> findByIsActiveTrueOrderByCodigoAsc();
}
