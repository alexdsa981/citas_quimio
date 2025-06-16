package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.AtencionQuimioterapia;
import com.ipor.quimioterapia.model.dynamic.Enfermera;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.model.fixed.Cubiculo;
import com.ipor.quimioterapia.model.other.DTO.AtencionQuimioterapiaDTO;
import com.ipor.quimioterapia.repository.dynamic.AtencionQuimioterapiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AtencionQuimioterapiaService {

    @Autowired
    AtencionQuimioterapiaRepository atencionQuimioterapiaRepository;



    public AtencionQuimioterapia crearOActualizar(
            AtencionQuimioterapiaDTO dto,
            FichaPaciente fichaPaciente,
            Medico medico,
            Enfermera enfermera,
            Cubiculo cubiculo
    ) {
        AtencionQuimioterapia atencion;

        if (fichaPaciente.getAtencionQuimioterapia() != null) {
            // Ya existe, actualizamos la existente
            atencion = fichaPaciente.getAtencionQuimioterapia();
        } else {
            atencion = new AtencionQuimioterapia();
        }

        atencion.setMedico(medico);
        atencion.setEnfermera(enfermera);
        atencion.setCubiculo(cubiculo);
        atencion.setHoraInicio(dto.getHoraInicio());
        atencion.setHoraFin(dto.getHoraFin());
        atencion.setDuracionMinutosProtocolo(dto.getDuracionMinutos());

        // Guardamos (si es nuevo o modificado)
        atencionQuimioterapiaRepository.save(atencion);
        return atencion;
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

}

