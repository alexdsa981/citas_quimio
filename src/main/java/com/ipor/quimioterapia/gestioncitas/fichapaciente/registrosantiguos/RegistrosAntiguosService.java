package com.ipor.quimioterapia.gestioncitas.fichapaciente.registrosantiguos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class RegistrosAntiguosService {
    @Autowired
    RegistrosAntiguosRepository registrosAntiguosRepository;

    public List<RegistrosAntiguos> registrosAntiguosEntreFechas(LocalDate desde, LocalDate hasta){
        return registrosAntiguosRepository.buscarFichasEntreFechas(desde, hasta);
    }

    public RegistrosAntiguos getPorID(Long id){
        return registrosAntiguosRepository.findById(id).get();

    }
}
