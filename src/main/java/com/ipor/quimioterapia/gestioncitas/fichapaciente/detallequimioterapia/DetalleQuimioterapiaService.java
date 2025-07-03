package com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.dto.DetalleQuimioterapiaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DetalleQuimioterapiaService {

    @Autowired
    DetalleQuimioterapiaRepository detalleQuimioterapiaRepository;

    public DetalleQuimioterapia guardar(DetalleQuimioterapiaDTO dto, FichaPaciente fichaPaciente) {
        DetalleQuimioterapia detalleQuimioterapia;
        if (fichaPaciente.getDetalleQuimioterapia() == null){
            detalleQuimioterapia = new DetalleQuimioterapia();
        }else{
            detalleQuimioterapia = fichaPaciente.getDetalleQuimioterapia();
        }
        detalleQuimioterapia.setAnamnesis(dto.getAnamnesis());
        detalleQuimioterapia.setEvolucion(dto.getEvolucion());
        detalleQuimioterapia.setMedicinas(dto.getMedicinas());
        detalleQuimioterapia.setExamenFisico(dto.getExamenFisico());
        detalleQuimioterapia.setTratamiento(dto.getTratamiento());
        detalleQuimioterapia.setObservaciones(dto.getObservaciones());
        detalleQuimioterapia.setExamenesAuxiliares(dto.getExamenesAuxiliares());
        detalleQuimioterapiaRepository.save(detalleQuimioterapia);
        return detalleQuimioterapia;
    }

    public void save(DetalleQuimioterapia detalleQuimioterapia){
        detalleQuimioterapiaRepository.save(detalleQuimioterapia);
    }
    public List<DetalleQuimioterapia> getLista() {
        return detalleQuimioterapiaRepository.findAll();
    }

    public DetalleQuimioterapia getPorID(Long id) {
        return detalleQuimioterapiaRepository.findById(id).get();
    }

}
