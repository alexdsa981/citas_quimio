package com.ipor.quimioterapia.recursos.personal.medico;

import com.ipor.quimioterapia.gestioncitas.dto.EmpleadoCrearDTO;
import com.ipor.quimioterapia.gestioncitas.logs.AccionLogGlobal;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
import com.ipor.quimioterapia.spring.usuario.SpringUserService;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;

    public void crearOActualizar(EmpleadoCrearDTO dto) {
        Optional<Medico> existente = medicoRepository.findById(dto.getId());
        Medico entidad;
        if (existente.isPresent()) {
            entidad = existente.get();
        } else {
            entidad = new Medico();
            entidad.setIdPersona(dto.getId());
        }

        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setNombreCompleto(dto.getNombreCompleto());
        entidad.setIsActive(Boolean.TRUE);


        //LOG GLOBAL----------------------
        String descripcion = String.format("El usuario %s agregó al colaborador %s como parte del personal médico. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                entidad.getNombreCompleto(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.AGREGA_MEDICO, descripcion);
        //-------------------------------




        medicoRepository.save(entidad);
    }

    public List<Medico> getLista(){
        return medicoRepository.findAllByOrderByNombreAsc();
    }
    public List<Medico> getListaActivos(){
        return medicoRepository.findByIsActiveTrueOrderByNombreAsc();
    }
    public Medico getPorID(Long id) {
        return medicoRepository.findById(id).get();
    }

    public void cambiarEstado(Long id, boolean isActive) {
        Medico entidad = medicoRepository.findById(id).get();
        entidad.setIsActive(isActive);
        medicoRepository.save(entidad);

        //LOG GLOBAL----------------------
        String esActivo = isActive? "ACTIVÓ" : "DESACTIVÓ";
        String descripcion = String.format("El usuario %s %s al médico %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                esActivo,
                entidad.getNombreCompleto(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.DESACTIVA_MEDICO, descripcion);
        //-------------------------------



    }
}
