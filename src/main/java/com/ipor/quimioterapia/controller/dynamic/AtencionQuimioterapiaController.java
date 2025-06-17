package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.*;
import com.ipor.quimioterapia.model.fixed.Cubiculo;
import com.ipor.quimioterapia.model.other.DTO.AtencionQuimioterapiaDTO;
import com.ipor.quimioterapia.service.dynamic.*;
import com.ipor.quimioterapia.service.fixed.CubiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/app/atencion-quimioterapia")
public class AtencionQuimioterapiaController {
    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    CubiculoService cubiculoService;
    @Autowired
    MedicoService medicoService;
    @Autowired
    EnfermeraService enfermeraService;
    @Autowired
    AtencionQuimioterapiaService atencionQuimioterapiaService;
    @Autowired
    CitaService citaService;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarAtencionQuimioterapia(@RequestBody AtencionQuimioterapiaDTO dto) {
        try {
            FichaPaciente ficha = fichaPacienteService.getPorID(dto.getIdFicha());
            Medico medico = medicoService.getPorID(dto.getIdMedico());
            Enfermera enfermera = enfermeraService.getPorID(dto.getIdEnfermera());
            Cubiculo cubiculo = cubiculoService.getPorID(dto.getIdCubiculo());

            AtencionQuimioterapia resultado = atencionQuimioterapiaService.crearOActualizar(dto, ficha, medico, enfermera, cubiculo);

            ficha.setAtencionQuimioterapia(resultado);
            fichaPacienteService.guardar(ficha);

            // Asume que si tiene valores completos, se pasa a pendiente
            boolean estadoPendiente = resultado.getDuracionMinutosProtocolo() != null && resultado.getDuracionMinutosProtocolo() > 0;
            if (estadoPendiente) {
                citaService.cambiarEstado(EstadoCita.PENDIENTE, ficha);
            } else {
                citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, ficha);
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", ficha.getAtencionQuimioterapia() != null ? "Protocolo actualizado correctamente" : "Protocolo creado correctamente"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al guardar Protocolo Atención: " + "Debe completar todos los campos"));
        }
    }





    @PostMapping("/ficha/{idFicha}")
    public ResponseEntity<?> obtenerAtencionQuimioterapia(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);

            AtencionQuimioterapia atencion = fichaPaciente.getAtencionQuimioterapia();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "enfermeraId", atencion.getEnfermera() != null ? atencion.getEnfermera().getIdPersona() : "",
                    "medicoId", atencion.getMedico() != null ? atencion.getMedico().getIdPersona() : "",
                    "cubiculoId", atencion.getCubiculo() != null ? atencion.getCubiculo().getId() : "",
                    "duracion", atencion.getDuracionMinutosProtocolo() != null ? atencion.getDuracionMinutosProtocolo() : 0
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }



}
