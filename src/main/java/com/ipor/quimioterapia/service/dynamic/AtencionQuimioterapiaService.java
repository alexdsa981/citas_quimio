package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.AtencionQuimioterapia;
import com.ipor.quimioterapia.model.dynamic.Enfermera;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.model.fixed.Cubiculo;
import com.ipor.quimioterapia.model.other.DTO.AtencionQuimioterapiaDTO;
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



    public AtencionQuimioterapia crear(Medico medico) {
        AtencionQuimioterapia atencionQuimioterapia = new AtencionQuimioterapia();
        atencionQuimioterapia.setMedico(medico);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
        return atencionQuimioterapia;
    }

    public List<AtencionQuimioterapia> getLista() {
        return atencionQuimioterapiaRepository.findAll();
    }


    public AtencionQuimioterapia getPorID(Long id) {
        return atencionQuimioterapiaRepository.findById(id).get();
    }

    public void reprogramarCita(FichaPaciente fichaPaciente,Medico medico) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapia();
        atencionQuimioterapia.setMedico(medico);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }


    public void pendienteProtocolo(FichaPaciente fichaPaciente) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapia();
        atencionQuimioterapia.setHoraFin(null);
        atencionQuimioterapia.setHoraInicio(null);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

    public void iniciarProtocolo(LocalTime horaInicio, FichaPaciente fichaPaciente) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapia();
        atencionQuimioterapia.setHoraInicio(horaInicio);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

    public void finalizarProtocolo(LocalTime horaFin,FichaPaciente fichaPaciente) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapia();
        atencionQuimioterapia.setHoraFin(horaFin);
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
    }

    public Boolean actualizar(AtencionQuimioterapiaDTO atencionQuimioterapiaDTO, FichaPaciente fichaPaciente, Medico medico, Cubiculo cubiculo, Enfermera enfermera) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapia();
        atencionQuimioterapia.setMedico(medico);
        atencionQuimioterapia.setCubiculo(cubiculo);
        atencionQuimioterapia.setEnfermera(enfermera);
        atencionQuimioterapia.setDuracionMinutosProtocolo(atencionQuimioterapiaDTO.getDuracionMinutos());
        atencionQuimioterapiaRepository.save(atencionQuimioterapia);
        if (atencionQuimioterapia.getEnfermera() != null && atencionQuimioterapia.getCubiculo() != null && atencionQuimioterapia.getDuracionMinutosProtocolo() != 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}

