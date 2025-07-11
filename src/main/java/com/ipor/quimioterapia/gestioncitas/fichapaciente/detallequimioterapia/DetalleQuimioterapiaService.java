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
        DetalleQuimioterapia detalle;

        if (fichaPaciente.getDetalleQuimioterapia() == null){
            detalle = new DetalleQuimioterapia();
        } else {
            detalle = fichaPaciente.getDetalleQuimioterapia();
        }

        detalle.setMedicinas(dto.getMedicinas());
        detalle.setTratamiento(dto.getTratamiento());
        detalle.setObservaciones(dto.getObservaciones());
        detalle.setExamenesAuxiliares(dto.getExamenesAuxiliares());

        return detalleQuimioterapiaRepository.save(detalle);
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
