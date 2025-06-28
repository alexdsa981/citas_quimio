package com.ipor.quimioterapia.gestioncitas.fichapaciente.atencionquimioterapia;

import com.ipor.quimioterapia.recursos.personal.enfermera.Enfermera;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.recursos.personal.medico.Medico;
import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.dto.AtencionQuimioterapiaDTO;
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

    public void reprogramarCita(FichaPaciente fichaPaciente) {
        AtencionQuimioterapia atencionQuimioterapia = fichaPaciente.getAtencionQuimioterapia();
        atencionQuimioterapia.setHoraInicio(null);
        atencionQuimioterapia.setHoraFin(null);
        atencionQuimioterapia.setMedico(null);
        atencionQuimioterapia.setEnfermera(null);
        atencionQuimioterapia.setCubiculo(null);
        atencionQuimioterapia.setDuracionMinutosProtocolo(null);
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

