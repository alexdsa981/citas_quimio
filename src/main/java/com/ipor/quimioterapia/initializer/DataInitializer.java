package com.ipor.quimioterapia.initializer;

import com.ipor.quimioterapia.model.other.RolUsuario;
import com.ipor.quimioterapia.repository.other.RolUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    @Override
    public void run(String... args) {


        // 1. Crear roles si no existen
        if (rolUsuarioRepository.count() == 0) {
            rolUsuarioRepository.save(new RolUsuario("General"));
            rolUsuarioRepository.save(new RolUsuario("Supervisor"));
            rolUsuarioRepository.save(new RolUsuario("Admin"));
        }


    }
}