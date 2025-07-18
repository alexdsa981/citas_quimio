package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipor.quimioterapia.gestioncitas.dto.CieCrearDTO;
import com.ipor.quimioterapia.gestioncitas.dto.CieGuardarListaDTO;
import com.ipor.quimioterapia.gestioncitas.dto.DetalleCieDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie.CieService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import com.ipor.quimioterapia.gestioncitas.logs.AccionLogFicha;
import com.ipor.quimioterapia.gestioncitas.logs.LogService;
import com.ipor.quimioterapia.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/diagnostico")
public class DiagnosticoController {

    @Autowired
    CieService cieService;
    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/cie/guardar")
    public ResponseEntity<?> guardarCie(@RequestBody CieCrearDTO dto) {
        cieService.crearOActualizar(dto);
        return ResponseEntity.ok("CIE registrado");
    }

    @PostMapping("/cie/guardar-lista")
    public ResponseEntity<?> guardarCieLista(@RequestBody CieGuardarListaDTO dto) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());

            // Obtener lista CIE anterior
            List<DetalleCie> anteriores = cieService.getPorIDFichaPaciente(fichaPaciente.getId());
            List<String> cieAnteriorLista = anteriores.stream()
                    .map(dc -> dc.getCie().getCodigo() + " - " + dc.getCie().getDescripcion())
                    .toList();

            String valorAnteriorJson = objectMapper.writeValueAsString(cieAnteriorLista);

            // Guardar nueva lista
            List<DetalleCie> listaActualizada = cieService.guardarListaDetalleCie(fichaPaciente, dto.getCieIds());

            // Obtener lista CIE nueva
            List<String> cieNuevoLista = listaActualizada.stream()
                    .map(dc -> dc.getCie().getCodigo() + " - " + dc.getCie().getDescripcion())
                    .toList();

            String valorNuevoJson = objectMapper.writeValueAsString(cieNuevoLista);

            // Descripción del log
            String descripcionLog = String.format(
                    "El usuario %s actualizó la lista de diagnósticos CIE de la ficha del paciente el %s.",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
            );

            logService.saveDeFicha(
                    usuarioService.getUsuarioLogeado(),
                    fichaPaciente,
                    AccionLogFicha.ACTUALIZAR_DIAGNOSTICO_CIE,
                    valorAnteriorJson,
                    valorNuevoJson,
                    descripcionLog
            );

            return ResponseEntity.ok(Map.of("detalleCies", listaActualizada));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("mensaje", "Error al guardar lista CIE: " + e.getMessage())
            );
        }
    }


    @GetMapping("/cie/lista/{idFicha}")
    public ResponseEntity<?> obtenerDetalleCiePorFicha(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaActual = fichaPacienteService.getPorID(idFicha);
            List<DetalleCie> lista = cieService.getPorIDFichaPaciente(idFicha);
            FichaPaciente fichaDeOrigen = fichaActual;

            if (lista == null || lista.isEmpty()) {
                // Buscar otras fichas del mismo paciente (excluyendo la actual)
                Paciente paciente = fichaActual.getPaciente();
                List<FichaPaciente> fichasAnteriores = fichaPacienteService
                        .findByPacienteOrderByFechaDesc(paciente.getIdPaciente(), idFicha);

                for (FichaPaciente ficha : fichasAnteriores) {
                    List<DetalleCie> listaTemporal = cieService.getPorIDFichaPaciente(ficha.getId());
                    if (listaTemporal != null && !listaTemporal.isEmpty()) {
                        lista = listaTemporal;
                        fichaDeOrigen = ficha;
                        break;
                    }
                }
            }

            List<DetalleCieDTO> dtoList = lista.stream()
                    .map(DetalleCieDTO::new)
                    .toList();

            return ResponseEntity.ok(Map.of(
                    "detalleCies", dtoList,
                    "fechaReferencia", fichaDeOrigen.getFechaCreacion()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "No se pudo obtener los diagnósticos para la ficha especificada."
            ));
        }
    }



}
