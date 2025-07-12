package com.ipor.quimioterapia.gestioncitas.fichapaciente.detallequimioterapia;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipor.quimioterapia.gestioncitas.dto.DetalleQuimioterapiaDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
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
@RequestMapping("/app/detalle-quimioterapia")
public class DetalleQuimioterapiaController {

    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    DetalleQuimioterapiaService detalleQuimioterapiaService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;
    @Autowired
    ObjectMapper objectMapper;


    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarDetalleQuimioterapia(@RequestBody DetalleQuimioterapiaDTO dto) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
            boolean esNuevo = (fichaPaciente.getDetalleQuimioterapia() == null);

            //LOG DETALLE QUIMIOTERAPIA---------------------------------------------------
            String valorAnteriorJson = null;
            if (!esNuevo) {
                Map<String, Object> valorAnteriorMap = Map.of(
                        "medicinas", fichaPaciente.getDetalleQuimioterapia().getMedicinas(),
                        "tratamiento", fichaPaciente.getDetalleQuimioterapia().getTratamiento(),
                        "observacion", fichaPaciente.getDetalleQuimioterapia().getObservaciones()
                );
                valorAnteriorJson = objectMapper.writeValueAsString(valorAnteriorMap);
            }
            //---------------------------------------------------

            DetalleQuimioterapia detalle = detalleQuimioterapiaService.guardar(dto, fichaPaciente);

            if (esNuevo) {
                fichaPaciente.setDetalleQuimioterapia(detalle);
                fichaPacienteService.save(fichaPaciente);
            }

            //LOG DETALLE QUIMIOTERAPIA---------------------------------------------------

            Map<String, Object> valorNuevoMap = Map.of(
                    "medicinas", fichaPaciente.getDetalleQuimioterapia().getMedicinas(),
                    "tratamiento", fichaPaciente.getDetalleQuimioterapia().getTratamiento(),
                    "observacion", fichaPaciente.getDetalleQuimioterapia().getObservaciones()
            );

            String valorNuevoJson = objectMapper.writeValueAsString(valorNuevoMap);

            String descripcionLog = String.format(
                    "El usuario %s actualizó los detalles de quimioterapia de la ficha del paciente el %s.",
                    usuarioService.getUsuarioLogeado().getNombre(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm"))
            );

            logService.saveDeFicha(usuarioService.getUsuarioLogeado(), fichaPaciente,
                    AccionLogFicha.ACTUALIZAR_DETALLE_QUIMIOTERAPIA,
                    valorAnteriorJson, valorNuevoJson, descripcionLog);

            //---------------------------------------------------


            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Detalle guardado correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al guardar detalle: " + e.getMessage()));
        }
    }



}
