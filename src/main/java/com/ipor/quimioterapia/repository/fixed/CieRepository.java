package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Cie;
import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CieRepository extends JpaRepository<Cie, Long> {
    List<Cie> findAllByOrderByCodigoAsc();
    List<Cie> findByIsActiveTrueOrderByCodigoAsc();
}
