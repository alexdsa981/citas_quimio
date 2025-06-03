package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.AtencionQuimioterapia;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.repository.dynamic.AtencionQuimioterapiaRepository;
import com.ipor.quimioterapia.service.fixed.CubiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AtencionQuimioterapiaService {

    @Autowired
    AtencionQuimioterapiaRepository atencionQuimioterapiaRepository;
    @Autowired
    CubiculoService cubiculoService;


    public void crear(FichaPaciente fichaPaciente, Medico medico) {
        AtencionQuimioterapia atencionQuimioterapia = new AtencionQuimioterapia();
        atencionQuimioterapia.setFichaPaciente(fichaPaciente);
        atencionQuimioterapia.setMedico(medico);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

    public List<AtencionQuimioterapia> getLista() {
        return atencionQuimioterapiaRepository.findAll();
    }


    public AtencionQuimioterapia getPorID(Long id) {
        return atencionQuimioterapiaRepository.findById(id).get();
    }

    public void pendienteProtocolo(FichaPaciente fichaPaciente) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapiaList().get(0);
        atencionQuimioterapia.setMedico(null);
        atencionQuimioterapia.setHoraInicio(null);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

    public void iniciarProtocolo(LocalTime horaInicio, FichaPaciente fichaPaciente) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapiaList().get(0);
        atencionQuimioterapia.setHoraInicio(horaInicio);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

    public void finalizarProtocolo(LocalTime horaFin, Medico medico, FichaPaciente fichaPaciente) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapiaList().get(0);
        atencionQuimioterapia.setHoraInicio(horaFin);
        atencionQuimioterapia.setMedico(medico);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

    public void actualizar(Long id, String nombre) {
        AtencionQuimioterapia atencionQuimioterapia = atencionQuimioterapiaRepository.findById(id).orElseThrow();
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

}

