package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.fixed.Cubiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CubiculoRepository extends JpaRepository<Cubiculo, Long> {

}
