package com.ipor.quimioterapia.initializer;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
import com.ipor.quimioterapia.model.other.RolUsuario;
import com.ipor.quimioterapia.repository.fixed.*;
import com.ipor.quimioterapia.repository.other.RolUsuarioRepository;

import com.ipor.quimioterapia.service.fixed.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    AseguradoraService aseguradoraService;
    @Autowired
    AseguradoraRepository aseguradoraRepository;

    @Autowired
    CieService cieService;
    @Autowired
    CieRepository cieRepository;

    @Autowired
    ContratanteService contratanteService;
    @Autowired
    ContratanteRepository contratanteRepository;

    @Autowired
    CubiculoService cubiculoService;
    @Autowired
    CubiculoRepository cubiculoRepository;

    @Autowired
    TipoDocIdentidadService tipoDocIdentidadService;
    @Autowired
    TipoDocIdentidadRepository tipoDocIdentidadRepository;

    @Autowired
    TipoPacienteService tipoPacienteService;
    @Autowired
    TipoPacienteRepository tipoPacienteRepository;


    @Override
    public void run(String... args) {

        if (rolUsuarioRepository.count() == 0) {
            rolUsuarioRepository.save(new RolUsuario("General"));
            rolUsuarioRepository.save(new RolUsuario("Supervisor"));
            rolUsuarioRepository.save(new RolUsuario("Admin"));
        }

        if (aseguradoraRepository.count() == 0) {
            aseguradoraService.crear("Rímac Seguros");
            aseguradoraService.crear("Pacífico EPS");
            aseguradoraService.crear("Mapfre Perú");
        }

        if (cieRepository.count() == 0) {
            cieService.crear("A00", "Cólera");
            cieService.crear("B20", "VIH");
            cieService.crear("C34", "Neoplasia maligna de bronquios y pulmón");
        }

        if (contratanteRepository.count() == 0) {
            contratanteService.crear("Ministerio de Salud");
            contratanteService.crear("EsSalud");
            contratanteService.crear("Sanidad de las Fuerzas Armadas");
        }

        if (cubiculoRepository.count() == 0) {
            cubiculoService.crear("Cubículo 1 - Sala Principal");
            cubiculoService.crear("Cubículo 2 - Sala de Aislamiento");
            cubiculoService.crear("Cubículo 3 - Pediatría");
        }

        if (tipoDocIdentidadRepository.count() == 0) {
            tipoDocIdentidadService.crear("DNI");
            tipoDocIdentidadService.crear("Carné de Extranjería");
            tipoDocIdentidadService.crear("Pasaporte");
        }

        if (tipoPacienteRepository.count() == 0) {
            tipoPacienteService.crear("Ambulatorio");
            tipoPacienteService.crear("Hospitalizado");
            tipoPacienteService.crear("Emergencia");
        }

    }
}