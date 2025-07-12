package com.ipor.quimioterapia.recursos.cubiculo;

import com.ipor.quimioterapia.gestioncitas.logs.AccionLogGlobal;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CubiculoService {

    @Autowired
    private CubiculoRepository cubiculoRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;

    public void crear(String codigo) {
        Cubiculo entidad = new Cubiculo();
        entidad.setCodigo(codigo);
        entidad.setIsActive(Boolean.TRUE);
        cubiculoRepository.save(entidad);

        //LOG GLOBAL----------------------
        String descripcion = String.format("El usuario %s agregó el cubículo %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                entidad.getCodigo(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.ACTUALIZA_CUBICULO, descripcion);
        //-------------------------------



    }


    public List<Cubiculo> getLista() {
        return cubiculoRepository.findAllByOrderByCodigoAsc();
    }

    public List<Cubiculo> getListaActivos() {
        return cubiculoRepository.findByIsActiveTrueOrderByCodigoAsc();
    }

    public Cubiculo getPorID(Long id) {
        return cubiculoRepository.findById(id).get();
    }

    public void actualizar(Long id, String codigo) {
        Cubiculo entidad = cubiculoRepository.findById(id).orElseThrow();
        String codigoAnterior = entidad.getCodigo();
        entidad.setCodigo(codigo);
        cubiculoRepository.save(entidad);

        //LOG GLOBAL----------------------
        String descripcion = String.format("El usuario %s actualizó el cubículo %s -> %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                codigoAnterior,
                entidad.getCodigo(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.ACTUALIZA_CUBICULO, descripcion);
        //-------------------------------

    }


    public void cambiarEstado(Long id, boolean isActive) {
        Cubiculo cubiculo = cubiculoRepository.findById(id).get();
        cubiculo.setIsActive(isActive);
        cubiculoRepository.save(cubiculo);


        //LOG GLOBAL----------------------
        String esActivo = isActive? "ACTIVÓ" : "DESACTIVÓ";
        String descripcion = String.format("El usuario %s %s el cubículo %s. Fecha: %s",
                usuarioService.getUsuarioLogeado().getNombre(),
                esActivo,
                cubiculo.getCodigo(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
        );

        logService.saveDeGlobal(usuarioService.getUsuarioLogeado(), AccionLogGlobal.DESACTIVA_CUBICULO, descripcion);
        //-------------------------------

    }
}
