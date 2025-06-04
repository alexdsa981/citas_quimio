package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.Enfermera;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.FuncionesVitales;
import com.ipor.quimioterapia.model.other.DTO.FuncionesVitalesDTO;
import com.ipor.quimioterapia.repository.dynamic.EnfermeraRepository;
import com.ipor.quimioterapia.repository.dynamic.FichaPacienteRepository;
import com.ipor.quimioterapia.repository.dynamic.FuncionesVitalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FuncionesVitalesService {

    @Autowired
    FuncionesVitalesRepository funcionesVitalesRepository;
    @Autowired
    FichaPacienteRepository fichaPacienteRepository;

    public void crear(FichaPaciente fichaPaciente) {
        FuncionesVitales funcionesVitales = new FuncionesVitales();
        funcionesVitales.setHora(LocalTime.now());
        funcionesVitales.setFecha(LocalDate.now());
        funcionesVitales.setFichaPaciente(fichaPaciente);
        funcionesVitalesRepository.save(funcionesVitales);
    }

    public void setDatoAFicha(FuncionesVitalesDTO dto, FichaPaciente fichaPaciente) {
        List<FuncionesVitales> lista = fichaPaciente.getFuncionesVitales();

        if (lista.isEmpty()) {
            // Por seguridad, creamos uno por si acaso
            FuncionesVitales nueva = new FuncionesVitales();
            nueva.setFecha(LocalDate.now());
            nueva.setHora(LocalTime.now());
            nueva.setFichaPaciente(fichaPaciente);
            lista.add(nueva); // Lo agregamos a la lista del paciente
        }

        FuncionesVitales fv = lista.get(0); // Seguro que existe en este punto

        fv.setPesoKg(dto.getPeso());
        fv.setTallaCm(dto.getTalla());
        fv.setPresionSistolica(dto.getPresionSistolica());
        fv.setPresionDiastolica(dto.getPresionDiastolica());
        fv.setFrecuenciaRespiratoria(dto.getFrecuenciaRespiratoria());
        fv.setFrecuenciaCardiaca(dto.getFrecuenciaCardiaca());
        fv.setTemperatura(dto.getTemperatura());
        fv.setSaturacionOxigeno(dto.getSaturacionOxigeno());
        fv.setSuperficieCorporal(dto.getSuperficieCorporal());

        funcionesVitalesRepository.save(fv);
    }


    public List<FuncionesVitales> getLista() {
        return funcionesVitalesRepository.findAll();
    }


    public FuncionesVitales getPorID(Long id) {
        return funcionesVitalesRepository.findById(id).get();
    }


}

