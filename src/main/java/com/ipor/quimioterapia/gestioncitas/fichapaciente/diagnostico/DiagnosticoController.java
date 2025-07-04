package com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico;

import com.ipor.quimioterapia.gestioncitas.dto.CieCrearDTO;
import com.ipor.quimioterapia.gestioncitas.dto.CieGuardarListaDTO;
import com.ipor.quimioterapia.gestioncitas.dto.DetalleCieDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.cie.CieService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.diagnostico.detallecie.DetalleCie;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.paciente.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/diagnostico")
public class DiagnosticoController {

    @Autowired
    CieService cieService;
    @Autowired
    FichaPacienteService fichaPacienteService;

    @PostMapping("/cie/guardar")
    public ResponseEntity<?> guardarCie(@RequestBody CieCrearDTO dto) {
        cieService.crearOActualizar(dto);
        return ResponseEntity.ok("CIE registrado");
    }

    @PostMapping("/cie/guardar-lista")
    public ResponseEntity<?> guardarCieLista(@RequestBody CieGuardarListaDTO dto) {
        FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
        List<DetalleCie> lista = cieService.guardarListaDetalleCie(fichaPaciente, dto.getCieIds());

        // O construyes un DTO si necesitas limpiar la respuesta
        return ResponseEntity.ok(Map.of("detalleCies", lista));
    }

    @GetMapping("/cie/lista/{idFicha}")
    public ResponseEntity<?> obtenerDetalleCiePorFicha(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaActual = fichaPacienteService.getPorID(idFicha);
            List<DetalleCie> lista = cieService.getPorIDFichaPaciente(idFicha);
            FichaPaciente fichaDeOrigen = fichaActual;

            if (lista == null || lista.isEmpty()) {
                // Buscar otras fichas del mismo paciente (excluyendo la actual)
                Paciente paciente = fichaActual.getCita().getPaciente();
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
