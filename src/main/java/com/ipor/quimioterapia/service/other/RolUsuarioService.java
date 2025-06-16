package com.ipor.quimioterapia.service.other;

import com.ipor.quimioterapia.model.other.RolUsuario;
import com.ipor.quimioterapia.repository.other.RolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolUsuarioService {
    @Autowired
    RolUsuarioRepository rolUsuarioRepository;

    public List<RolUsuario> getLista(){
        return rolUsuarioRepository.findAll();
    }


}
