package com.ipor.quimioterapia.repository.dynamic;

import com.ipor.quimioterapia.model.dynamic.FuncionesVitales;
import com.ipor.quimioterapia.model.fixed.Aseguradora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionesVitalesRepository extends JpaRepository<FuncionesVitales, Long> {

}
