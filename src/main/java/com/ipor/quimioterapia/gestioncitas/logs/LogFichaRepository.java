package com.ipor.quimioterapia.gestioncitas.logs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LogFichaRepository extends JpaRepository<LogFicha, Long> {

    List<LogFicha> findByFichaPaciente_Id(Long idFicha);

}
