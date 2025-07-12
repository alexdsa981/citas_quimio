package com.ipor.quimioterapia.recursos.personal.enfermera;

import com.ipor.quimioterapia.gestioncitas.dto.EmpleadoCrearDTO;
import com.ipor.quimioterapia.gestioncitas.logs.AccionLogGlobal;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EnfermeraService {

    @Autowired
    EnfermeraRepository enfermeraRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;
    public void crearOActualizar(EmpleadoCrearDTO dto) {
        Optional<Enfermera> existente = enfermeraRepository.findById(dto.getId());
        Enfermera entidad;
        if (existente.isPresent()) {
            entidad = existente.get();
        } else {
            entidad = new Enfermera();
            entidad.setIdPersona(dto.getId());
        }

        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setNombreCompleto(dto.getNombreCompleto());
        entidad.setIsActive(Boolean.TRUE);
        enfermeraRepository.save(entidad);

        //LOG GLOBAL----------------------
        String descripcion = String.format("El usuario %s agregó al colaborador %s como parte del personal en enfermeria. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                entidad.getNombreCompleto(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.AGREGA_ENFERMERA, descripcion);
        //-------------------------------




    }

    public List<Enfermera> getLista() {
        return enfermeraRepository.findAllByOrderByNombreAsc();
    }

    public List<Enfermera> getListaActivos() {
        return enfermeraRepository.findByIsActiveTrueOrderByNombreAsc();
    }

    public Enfermera getPorID(Long id) {
        return enfermeraRepository.findById(id).get();
    }


    public void cambiarEstado(Long id, boolean isActive) {
        Enfermera entidad = enfermeraRepository.findById(id).get();
        entidad.setIsActive(isActive);
        enfermeraRepository.save(entidad);



        //LOG GLOBAL----------------------
        String esActivo = isActive? "ACTIVÓ" : "DESACTIVÓ";
        String descripcion = String.format("El usuario %s %s a la enfermera %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                esActivo,
                entidad.getNombreCompleto(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.DESACTIVA_ENFERMERA, descripcion);
        //-------------------------------

    }
}

