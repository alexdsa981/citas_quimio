package com.ipor.quimioterapia.gestioncitas.fichapaciente.funcionesvitales;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.dto.FuncionesVitalesDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class FuncionesVitalesService {

    @Autowired
    FuncionesVitalesRepository funcionesVitalesRepository;
    @Autowired
    FichaPacienteRepository fichaPacienteRepository;



    public FuncionesVitales setDatoAFicha(FuncionesVitalesDTO dto, FichaPaciente fichaPaciente) {
        FuncionesVitales fv;

        if (fichaPaciente.getFuncionesVitales() == null) {
            fv = new FuncionesVitales();
        } else {
            fv = fichaPaciente.getFuncionesVitales();
        }

        fv.setPesoKg(dto.getPeso());
        fv.setTallaCm(dto.getTalla());
        fv.setPresionSistolica(dto.getPresionSistolica());
        fv.setPresionDiastolica(dto.getPresionDiastolica());
        fv.setFrecuenciaRespiratoria(dto.getFrecuenciaRespiratoria());
        fv.setFrecuenciaCardiaca(dto.getFrecuenciaCardiaca());
        fv.setTemperatura(dto.getTemperatura());
        fv.setSaturacionOxigeno(dto.getSaturacionOxigeno());
        fv.setSuperficieCorporal(dto.getSuperficieCorporal());

        return funcionesVitalesRepository.save(fv);
    }

    public  void save(FuncionesVitales funcionesVitales){
        funcionesVitalesRepository.save(funcionesVitales);
    }

    public List<FuncionesVitales> getLista() {
        return funcionesVitalesRepository.findAll();
    }


    public FuncionesVitales getPorID(Long id) {
        return funcionesVitalesRepository.findById(id).get();
    }


}

