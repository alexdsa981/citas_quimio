package com.ipor.quimioterapia.gestioncitas.botones.reprogramar;

import com.ipor.quimioterapia.gestioncitas.logs.AccionLogGlobal;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReprogramacionService {
    @Autowired
    ReprogramacionRepository reprogramacionRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;


    public Reprogramacion getPorID(Long id) {
        return reprogramacionRepository.findById(id).get();
    }

    public void save(Reprogramacion reprogramacion){
        reprogramacionRepository.save(reprogramacion);
    }
    public void delete(Long id){
        reprogramacionRepository.deleteById(id);
    }

}
