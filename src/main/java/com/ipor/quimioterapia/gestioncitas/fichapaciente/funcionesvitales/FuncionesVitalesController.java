package com.ipor.quimioterapia.gestioncitas.fichapaciente.funcionesvitales;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipor.quimioterapia.gestioncitas.dto.FuncionesVitalesDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.logs.AccionLogFicha;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/app/funciones-vitales")
public class FuncionesVitalesController {

    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    FuncionesVitalesService funcionesVitalesService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarSignosVitales(@RequestBody FuncionesVitalesDTO dto) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
            boolean esNuevo = (fichaPaciente.getFuncionesVitales() == null);

            // LOG FUNCIONES VITALES ---------------------------------------------------
            String valorAnteriorJson = null;
            if (!esNuevo) {
                Map<String, Object> valorAnteriorMap = Map.of(
                        "presDiast", fichaPaciente.getFuncionesVitales().getPresionDiastolica().toString(),
                        "presSist", fichaPaciente.getFuncionesVitales().getPresionSistolica().toString(),
                        "frecResp", fichaPaciente.getFuncionesVitales().getFrecuenciaRespiratoria().toString(),
                        "frecCard", fichaPaciente.getFuncionesVitales().getFrecuenciaCardiaca().toString(),
                        "temperatura", fichaPaciente.getFuncionesVitales().getTemperatura().toString(),
                        "satOxig", fichaPaciente.getFuncionesVitales().getSaturacionOxigeno().toString(),
                        "pesoKG", fichaPaciente.getFuncionesVitales().getPesoKg().toString(),
                        "tallaCM", fichaPaciente.getFuncionesVitales().getTallaCm().toString(),
                        "subCorp", fichaPaciente.getFuncionesVitales().getSuperficieCorporal().toString()
                );
                valorAnteriorJson = objectMapper.writeValueAsString(valorAnteriorMap);
            }

            FuncionesVitales fv = funcionesVitalesService.setDatoAFicha(dto, fichaPaciente);

            if (esNuevo) {
                fichaPaciente.setFuncionesVitales(fv);
                fichaPacienteService.save(fichaPaciente);
            }

            // LOG NUEVOS DATOS ---------------------------------------------------
            Map<String, Object> valorNuevoMap = Map.of(
                    "presDiast", fichaPaciente.getFuncionesVitales().getPresionDiastolica().toString(),
                    "presSist", fichaPaciente.getFuncionesVitales().getPresionSistolica().toString(),
                    "frecResp", fichaPaciente.getFuncionesVitales().getFrecuenciaRespiratoria().toString(),
                    "frecCard", fichaPaciente.getFuncionesVitales().getFrecuenciaCardiaca().toString(),
                    "temperatura", fichaPaciente.getFuncionesVitales().getTemperatura().toString(),
                    "satOxig", fichaPaciente.getFuncionesVitales().getSaturacionOxigeno().toString(),
                    "pesoKG", fichaPaciente.getFuncionesVitales().getPesoKg().toString(),
                    "tallaCM", fichaPaciente.getFuncionesVitales().getTallaCm().toString(),
                    "subCorp", fichaPaciente.getFuncionesVitales().getSuperficieCorporal().toString()
            );

            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);

            String descripcionLog = String.format(
                    "El usuario %s actualizó los Signos Vitales de la ficha del paciente el %s.",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
            );

            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,
                    AccionLogFicha.ACTUALIZAR_FUNCIONES_VITALES,
                    valorAnteriorJson, valorNuevoJson, descripcionLog);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Signos vitales guardados correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("mensaje", "Error al guardar signos vitales: " + e.getMessage())
            );
        }
    }


}
