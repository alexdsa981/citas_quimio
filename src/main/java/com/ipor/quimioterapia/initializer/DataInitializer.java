package com.ipor.quimioterapia.initializer;

import com.ipor.quimioterapia.model.fixed.Aseguradora;
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





        // Crear Tipo Paciente
        if (tipoPacienteRepository.count() == 0) {
            tipoPacienteService.crear("CRP");    // ID: 1
            tipoPacienteService.crear("Convenio");  // ID: 2
            tipoPacienteService.crear("Particular");     // ID: 3
        }

        // Crear Aseguradoras asociadas a cada Tipo Paciente
        if (aseguradoraRepository.count() == 0) {
            // Para Ambulatorio (tipoPacienteId = 1)
            aseguradoraService.crear("Rímac Seguros", 1L);       // ID: 1
            aseguradoraService.crear("Pacífico EPS", 1L);        // ID: 2

            // Para Hospitalizado (tipoPacienteId = 2)
            aseguradoraService.crear("Mapfre Perú", 2L);         // ID: 3
            aseguradoraService.crear("La Positiva", 2L);         // ID: 4
            aseguradoraService.crear("SIS", 2L);                 // ID: 5

            // Para Emergencia (tipoPacienteId = 3)
            aseguradoraService.crear("Seguros SURA", 3L);        // ID: 6
        }

        // Crear Contratantes asociados a cada Aseguradora
        if (contratanteRepository.count() == 0) {
            // Para Rímac Seguros (aseguradoraId = 1)
            contratanteService.crear("Ministerio de Salud", 1L);     // ID: 1
            contratanteService.crear("EsSalud", 1L);                 // ID: 2

            // Para Pacífico EPS (aseguradoraId = 2)
            contratanteService.crear("Sanidad de las Fuerzas Armadas", 2L); // ID: 3

            // Para Mapfre Perú (aseguradoraId = 3)
            contratanteService.crear("Clínica Internacional", 3L);   // ID: 4
            contratanteService.crear("RedSalud", 3L);                // ID: 5

            // Para La Positiva (aseguradoraId = 4)
            contratanteService.crear("Policía Nacional", 4L);        // ID: 6

            // Para SIS (aseguradoraId = 5)
            contratanteService.crear("Ministerio de Inclusión Social", 5L); // ID: 7

            // Para SURA (aseguradoraId = 6)
            contratanteService.crear("Fuerza Aérea del Perú", 6L);   // ID: 8
            contratanteService.crear("Ejército del Perú", 6L);       // ID: 9
        }







        if (cieRepository.count() == 0) {
            cieService.crear("A00", "Cólera");
            cieService.crear("B20", "VIH");
            cieService.crear("C34", "Neoplasia maligna de bronquios y pulmón");
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

        if (medicoRepository.count() == 0) {
            medicoService.crear("Juan", "Pérez", "Gómez");
            medicoService.crear("María", "Rodríguez", "Flores");
            medicoService.crear("Luis", "García", "Torres");
        }

        if (enfermeraRepository.count() == 0) {
            enfermeraService.crear("Ana", "Ramírez", "Cruz");
            enfermeraService.crear("Pedro", "Salas", "Mejía");
            enfermeraService.crear("Lucía", "Vega", "Huamán");
        }


    }
}