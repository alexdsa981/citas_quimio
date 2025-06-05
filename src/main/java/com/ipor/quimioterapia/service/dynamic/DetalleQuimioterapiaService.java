package com.ipor.quimioterapia.service.dynamic;

import com.ipor.quimioterapia.model.dynamic.DetalleQuimioterapia;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.other.DTO.DetalleQuimioterapiaDTO;
import com.ipor.quimioterapia.repository.dynamic.DetalleQuimioterapiaRepository;
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
        detalleQuimioterapia.setFechaModificacion(LocalDate.now());
        detalleQuimioterapia.setHoraModificacion(LocalTime.now());
        detalleQuimioterapiaRepository.save(detalleQuimioterapia);
        return detalleQuimioterapia;
    }


    public List<DetalleQuimioterapia> getLista() {
        return detalleQuimioterapiaRepository.findAll();
    }

    public DetalleQuimioterapia getPorID(Long id) {
        return detalleQuimioterapiaRepository.findById(id).get();
    }

}
