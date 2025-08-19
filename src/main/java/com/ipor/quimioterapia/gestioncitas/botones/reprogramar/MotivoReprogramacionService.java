package com.ipor.quimioterapia.gestioncitas.botones.reprogramar;

import com.ipor.quimioterapia.gestioncitas.logs.AccionLogGlobal;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
import com.ipor.quimioterapia.recursos.cubiculo.Cubiculo;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MotivoReprogramacionService {
    @Autowired
    MotivoReprogramacionRepository motivoReprogramacionRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;

    public void crear(String nombre) {
        MotivoReprogramacion entidad = new MotivoReprogramacion();
        entidad.setNombre(nombre);
        entidad.setIsActive(Boolean.TRUE);
        motivoReprogramacionRepository.save(entidad);

        //LOG GLOBAL----------------------
        String descripcion = String.format("El usuario %s agregó el motivo de reprogramacion %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                entidad.getNombre(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.ACTUALIZA_MOTIVO_REPROGRAMACION, descripcion);
        //-------------------------------



    }


    public List<MotivoReprogramacion> getLista() {
        return motivoReprogramacionRepository.findAllByOrderByNombreAsc();
    }

    public List<MotivoReprogramacion> getListaActivos() {
        return motivoReprogramacionRepository.findByIsActiveTrueOrderByNombreAsc();
    }

    public MotivoReprogramacion getPorID(Long id) {
        return motivoReprogramacionRepository.findById(id).get();
    }

    public void actualizar(Long id, String codigo) {
        MotivoReprogramacion entidad = motivoReprogramacionRepository.findById(id).orElseThrow();
        String nombreAnterior = entidad.getNombre();
        motivoReprogramacionRepository.save(entidad);

        //LOG GLOBAL----------------------
        String descripcion = String.format("El usuario %s actualizó el Motivo Reprogramación  %s -> %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                nombreAnterior,
                entidad.getNombre(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.ACTUALIZA_CUBICULO, descripcion);
        //-------------------------------

    }


    public void cambiarEstado(Long id, boolean isActive) {
        MotivoReprogramacion motivoReprogramacion = motivoReprogramacionRepository.findById(id).get();
        motivoReprogramacion.setIsActive(isActive);
        motivoReprogramacionRepository.save(motivoReprogramacion);


        //LOG GLOBAL----------------------
        String esActivo = isActive? "ACTIVÓ" : "DESACTIVÓ";
        String descripcion = String.format("El usuario %s %s el cubículo %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                esActivo,
                motivoReprogramacion.getNombre(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.DESACTIVA_CUBICULO, descripcion);

        //-------------------------------

    }


}
