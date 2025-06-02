package com.ipor.quimioterapia.controller.other;

import com.ipor.quimioterapia.model.dynamic.EstadoCita;
import com.ipor.quimioterapia.model.dynamic.FichaPaciente;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.model.other.FinalizarProtocoloDTO;
import com.ipor.quimioterapia.model.other.IniciarProtocoloDTO;
import com.ipor.quimioterapia.service.dynamic.AtencionQuimioterapiaService;
import com.ipor.quimioterapia.service.dynamic.CitaService;
import com.ipor.quimioterapia.service.dynamic.FichaPacienteService;
import com.ipor.quimioterapia.service.dynamic.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app/protocolo")
public class ProtocoloController {
    @Autowired
    CitaService citaService;
    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    AtencionQuimioterapiaService atencionQuimioterapiaService;
    @Autowired
    MedicoService medicoService;

    @PostMapping("/pendiente")
    public ResponseEntity<?> regresarProtocoloPendiente(@RequestBody Map<String, Long> body) {
        try {
            Long idFicha = body.get("idFicha");
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);

            citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaPaciente);
            atencionQuimioterapiaService.pendienteProtocolo(fichaPaciente);

            return ResponseEntity.ok(Map.of("message", "Protocolo regresado a Pendiente correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al regresar protocolo a Pendiente: " + e.getMessage()));
        }
    }


    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarProtocolo(@RequestBody IniciarProtocoloDTO iniciarProtocoloDTO) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(iniciarProtocoloDTO.getIdFicha());

            citaService.cambiarEstado(EstadoCita.EN_PROCESO, fichaPaciente);
            atencionQuimioterapiaService.iniciarProtocolo(iniciarProtocoloDTO.getHoraInicio(), fichaPaciente);
            return ResponseEntity.ok(Map.of("message", "Protocolo Iniciado correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al iniciar protocolo: " + e.getMessage()));
        }
    }

    @PostMapping("/finalizar")
    public ResponseEntity<?> iniciarProtocolo(@RequestBody FinalizarProtocoloDTO finalizarProtocoloDTO) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(finalizarProtocoloDTO.getIdFicha());
            Medico medico = medicoService.getPorID(finalizarProtocoloDTO.getIdMedico());

            citaService.cambiarEstado(EstadoCita.ATENDIDO, fichaPaciente);
            atencionQuimioterapiaService.finalizarProtocolo(finalizarProtocoloDTO.getHoraFin(), medico, fichaPaciente);
            return ResponseEntity.ok(Map.of("message", "Protocolo Iniciado correctamente"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al iniciar protocolo: " + e.getMessage()));
        }
    }

}
