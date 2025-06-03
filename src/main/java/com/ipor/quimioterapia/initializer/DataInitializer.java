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
    TipoEntradaService tipoEntradaService;
    @Autowired
    TipoEntradaRepository tipoEntradaRepository;


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


        if (tipoEntradaRepository.count() == 0) {
            tipoEntradaService.crear("AMBULATORIO");
            tipoEntradaService.crear("HOSPITALIZADO");
        }


        // Crear Tipo Paciente
        if (tipoPacienteRepository.count() == 0) {
            tipoPacienteService.crear("CRP");    // ID: 1
            tipoPacienteService.crear("CONVENIO");  // ID: 2
            tipoPacienteService.crear("PARTICULAR");     // ID: 3
        }

        // Crear Aseguradoras asociadas a cada Tipo Paciente
        if (aseguradoraRepository.count() == 0) {
            // PARA CRP (tipoPacienteId = 1)
            aseguradoraService.crear("ADMINISTRADORA CLINICA RICARDO PALMA", 1L); //1
            // PARA CONVENIO (tipoPacienteId = 2)
            aseguradoraService.crear("ADMINISTRADORA CLINICA RICARDO PALMA", 2L); //2
            aseguradoraService.crear("ESALUD", 2L); //3
            aseguradoraService.crear("FOPASEP", 2L); //4
            aseguradoraService.crear("IAFAS DE LA MARINA DE GUERRA DEL PERU", 2L); //5
            aseguradoraService.crear("IPOR (SEDE SAN BORJA)", 2L); //6
            aseguradoraService.crear("MINSA", 2L); //7
            aseguradoraService.crear("NINGUNO", 2L); //8
            aseguradoraService.crear("O-BNP PARIBAS CARDIF SA", 2L); //9
            aseguradoraService.crear("O-FEBAN", 2L); //10
            aseguradoraService.crear("ONCO - FAP", 2L); //11
            aseguradoraService.crear("O-ONCOSALUD SAC", 2L); //12
            aseguradoraService.crear("O-SALUDPOL-HNLNS-PNP", 2L); //13
            aseguradoraService.crear("SAN PABLO - ALMENARA", 2L); //14
            aseguradoraService.crear("VESALIO SA", 2L); //15
            // PARA PARTICULAR (tipoPacienteId = 3)
            aseguradoraService.crear("PARTICULAR", 3L); //16
            aseguradoraService.crear("PARTICULAR MEDICO", 3L); //17



        }

        // Crear Contratantes asociados a cada Aseguradora
        if (contratanteRepository.count() == 0) {
            // Para ADMINISTRADORA CLINICA RICARDO PALMA (aseguradoraId = 1)
            contratanteService.crear("ELECTRO PERU SA (51690)", 1L);
            contratanteService.crear("FEBAN (40047)", 1L);
            contratanteService.crear("MAFRE PERU SA ENTIDAD PRESTADORA DE SALUD (70008)", 1L);
            contratanteService.crear("MAPFRE COMPAÑIA DE SEGUROS (20008)", 1L);
            contratanteService.crear("NINGUNO", 1L);
            contratanteService.crear("ONCOSALUD (50548)", 1L);
            contratanteService.crear("PACIFICO PERUANO SUIZA (20003)", 1L);
            contratanteService.crear("PACIFICO SA ENTIDAD PRESTADORA DE SALUD (70004)", 1L);
            contratanteService.crear("PARTICULAR (1000)", 1L);
            contratanteService.crear("PETROLEOS DEL PERU - PETROPERU (40051)", 1L);
            contratanteService.crear("PLAN SALUD (90001 - 55986)", 1L);
            contratanteService.crear("PLAN SALUD ADULTO MAYOR (90001 - 57669)", 1L);
            contratanteService.crear("PROGRAMA DE APOYO SOCIAL (10019)", 1L);
            contratanteService.crear("RIMAC SA ENTIDAD PRESTADORA (70001)", 1L);
            contratanteService.crear("RIMAC SEGUROS Y REASEGUROS (20026)", 1L);
            contratanteService.crear("SUNAT", 1L);

            // Para ADMINISTRADORA CLINICA RICARDO PALMA (aseguradoraId = 2)
            contratanteService.crear("LA POSITIVA SANITAS EPS (70011)", 2L);

            // Para ESSALUD (aseguradoraId = 3)
            contratanteService.crear("RAS. ALMENARA", 3L);
            contratanteService.crear("RAS. AREQUIPA", 3L);
            contratanteService.crear("RAS. CUSCO", 3L);
            contratanteService.crear("RAS. REBAGLIATI", 3L);
            contratanteService.crear("RAS. SABOGAL", 3L);
            // Para FOPASEP (aseguradoraId = 4)
            contratanteService.crear("FOPASEP", 4L);
            // Para IAFAS DE LA MARINA DE GUERRA DEL PERU (aseguradoraId = 5)
            contratanteService.crear("IAFAS DE LA MARINA DE GUERRA DEL PERU", 5L);
            // Para IPOR (SEDE SAN BORJA) (aseguradoraId = 6)
            contratanteService.crear("PARTICULAR", 6L);
            // Para MINSA (aseguradoraId = 7)
            contratanteService.crear("HOSPITAL NACIONAL DOS DE MAYO", 7L);
            contratanteService.crear("INSTITUTO NACIONAL DEL NIÑO", 7L);
            contratanteService.crear("INSTITUTO NACIONAL DE CIENCIAS NEUROLOGICAS", 7L);
            // Para NINGUNO (aseguradoraId = 8)
            contratanteService.crear("NINGUNO", 8L);
            // Para O-BNP PARIBAS CARDIF SA (aseguradoraId = 9)
            contratanteService.crear("BNP PARIBAS CARDIF SA", 9L);
            // Para O-FEBAN (aseguradoraId = 10)
            contratanteService.crear("FONDOS DE EMPLEADOS DEL BANCO DE LA NACION", 10L);
            // Para ONCO - FAP (aseguradoraId = 11)
            contratanteService.crear("ONCO - FAP", 11L);
            // Para O-ONCOSALUD SAC (aseguradoraId = 12)
            contratanteService.crear("ONCOSALUD", 12L);
            // Para O-SALUDPOL-HNLNS-PNP (aseguradoraId = 13)
            contratanteService.crear("SALUDPOL", 13L);
            // Para SAN PABLO - ALMENARA (aseguradoraId = 14)
            contratanteService.crear("SAN PABLO - ALMENARA", 14L);
            // Para VESALIO SA (aseguradoraId = 15)
            contratanteService.crear("BNP PARIBAS CARDIF", 15L);
            contratanteService.crear("DOE RUN", 15L);
            contratanteService.crear("FONDO EMPLEADOS BCRP", 15L);
            contratanteService.crear("INMUEBLES Y RECUPERAC. CONTINENTAL BBVA", 15L);
            contratanteService.crear("LA POSITIVA SANITAS EPS", 15L);
            contratanteService.crear("LA POSITIVA SEGUROS Y REASEGUROS", 15L);
            contratanteService.crear("PACIFICO PERUANO SUIZA", 15L);
            contratanteService.crear("RIMAC SEGUROS Y REASEGUROS", 15L);
            contratanteService.crear("VESALIO SA", 15L);
            // Para VESALIO SA (aseguradoraId = 16)
            contratanteService.crear("ONCOLOGIA", 16L);
            // Para VESALIO SA (aseguradoraId = 17)
            contratanteService.crear("PARTICULAR", 17L);


        }

        if (cieRepository.count() == 0) {

        }



        if (cubiculoRepository.count() == 0) {
            cubiculoService.crear("Cubículo A");
            cubiculoService.crear("Cubículo B");
            cubiculoService.crear("Cubículo C");
            cubiculoService.crear("Cubículo D");
            cubiculoService.crear("Cubículo E");
        }

        if (tipoDocIdentidadRepository.count() == 0) {
            tipoDocIdentidadService.crear("DNI");
            tipoDocIdentidadService.crear("Carnet Ext.");
            tipoDocIdentidadService.crear("Pasaporte");
            tipoDocIdentidadService.crear("PTP");

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