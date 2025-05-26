package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.fixed.Contratante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratanteRepository extends JpaRepository<Contratante, Long> {

}
