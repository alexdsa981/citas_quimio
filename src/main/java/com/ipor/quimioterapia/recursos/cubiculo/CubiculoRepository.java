package com.ipor.quimioterapia.recursos.cubiculo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CubiculoRepository extends JpaRepository<Cubiculo, Long> {
    List<Cubiculo> findAllByOrderByCodigoAsc();
    List<Cubiculo> findByIsActiveTrueOrderByCodigoAsc();
}
