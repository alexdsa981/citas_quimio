package com.ipor.quimioterapia.gestioncitas.logs;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    @Autowired
    LogFichaRepository logFichaRepository;
    @Autowired
    LogGlobalRepository logGlobalRepository;

    //GLOBAL
    public void saveDeGlobal(LogGlobal logGlobal) {

    }

    public List<LogGlobal> getListaGlobal() {
        return logGlobalRepository.findAll();
    }

    //POR FICHA

    public void saveDeFicha(Usuario usuarioLogeado, FichaPaciente fichaPaciente, AccionLogFicha accion, String valorActual, String valorNuevo ,String descripcion) {
        LogFicha logFicha = new LogFicha();
        logFicha.setFichaPaciente(fichaPaciente);
        logFicha.setUsuario(usuarioLogeado);
        logFicha.setAccion(String.valueOf(accion));
        logFicha.setFechaHora(LocalDateTime.now());
        logFicha.setValorAnterior(valorActual);
        logFicha.setValorNuevo(valorNuevo);
        logFicha.setDescripcion(descripcion);
        logFichaRepository.save(logFicha);
    }

    public List<LogFicha> getListaFicha(Long idFicha) {
        return logFichaRepository.findByFichaPaciente_Id(idFicha);
    }

}
