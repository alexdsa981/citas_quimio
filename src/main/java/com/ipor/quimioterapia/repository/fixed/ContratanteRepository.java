package com.ipor.quimioterapia.repository.fixed;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.fixed.Contratante;
import com.ipor.quimioterapia.model.fixed.TipoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratanteRepository extends JpaRepository<Contratante, Long> {
    List<Contratante> findAllByOrderByNombreAsc();
    List<Contratante> findByIsActiveTrueOrderByNombreAsc();
    List<Contratante> findByAseguradoraIdAndIsActiveTrueOrderByNombreAsc(Long idAseguradora);
}
