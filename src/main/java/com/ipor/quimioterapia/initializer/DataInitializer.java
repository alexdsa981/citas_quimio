package com.ipor.quimioterapia.initializer;

import com.ipor.quimioterapia.model.other.RolUsuario;
import com.ipor.quimioterapia.repository.dynamic.EnfermeraRepository;
import com.ipor.quimioterapia.repository.dynamic.MedicoRepository;
import com.ipor.quimioterapia.repository.fixed.*;
import com.ipor.quimioterapia.repository.other.RolUsuarioRepository;

import com.ipor.quimioterapia.service.dynamic.EnfermeraService;
import com.ipor.quimioterapia.service.dynamic.MedicoService;
import com.ipor.quimioterapia.service.fixed.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

     //
     //CLASIFICADORES
     //

    @Autowired
    RolUsuarioRepository rolUsuarioRepository;



    @Autowired
    CieService cieService;
    @Autowired
    CieRepository cieRepository;


    @Autowired
    CubiculoService cubiculoService;
    @Autowired
    CubiculoRepository cubiculoRepository;



    @Autowired
    MedicoService medicoService;
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    EnfermeraService enfermeraService;
    @Autowired
    EnfermeraRepository enfermeraRepository;


    @Override
    public void run(String... args) {

        if (rolUsuarioRepository.count() == 0) {
            rolUsuarioRepository.save(new RolUsuario("General"));
            rolUsuarioRepository.save(new RolUsuario("Supervisor"));
            rolUsuarioRepository.save(new RolUsuario("Admin"));
        }

    }
}