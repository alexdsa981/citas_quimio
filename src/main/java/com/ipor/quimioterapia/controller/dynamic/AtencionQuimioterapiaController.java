package com.ipor.quimioterapia.controller.dynamic;

import com.ipor.quimioterapia.model.dynamic.*;
import com.ipor.quimioterapia.model.fixed.Cubiculo;
import com.ipor.quimioterapia.model.fixed.TipoEntrada;
import com.ipor.quimioterapia.model.other.DTO.AtencionQuimioterapiaDTO;
import com.ipor.quimioterapia.model.other.DTO.CitaCreadaDTO;
import com.ipor.quimioterapia.service.dynamic.*;
import com.ipor.quimioterapia.service.fixed.CubiculoService;
import com.ipor.quimioterapia.service.fixed.TipoEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> guardarAtencionQuimioterapia(@RequestBody AtencionQuimioterapiaDTO atencionQuimioterapiaDTO) {
        try {

            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(atencionQuimioterapiaDTO.getIdFicha());
            Medico medico = medicoService.getPorID(atencionQuimioterapiaDTO.getIdMedico());
            Cubiculo cubiculo = cubiculoService.getPorID(atencionQuimioterapiaDTO.getIdCubiculo());
            Enfermera enfermera = enfermeraService.getPorID(atencionQuimioterapiaDTO.getIdEnfermera());

            Boolean estadoCitaPendiente = atencionQuimioterapiaService.actualizar(atencionQuimioterapiaDTO, fichaPaciente, medico, cubiculo, enfermera);
            if (estadoCitaPendiente){
                citaService.cambiarEstado(EstadoCita.PENDIENTE, fichaPaciente);
            }else{
                citaService.cambiarEstado(EstadoCita.NO_ASIGNADO, fichaPaciente);
            }
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Protocolo Atención guardado correctamente"
            ));
        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al guardar Protocolo Atención: " + e.getMessage()));
        }
    }


    @PostMapping("/ficha/{idFicha}")
    public ResponseEntity<?> obtenerAtencionQuimioterapia(@PathVariable Long idFicha) {
        try {
            FichaPaciente fichaPaciente = fichaPacienteService.getPorID(idFicha);

            AtencionQuimioterapia atencion = fichaPaciente.getAtencionQuimioterapia();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "enfermeraId", atencion.getEnfermera() != null ? atencion.getEnfermera().getId() : "",
                    "medicoId", atencion.getMedico() != null ? atencion.getMedico().getId() : "",
                    "cubiculoId", atencion.getCubiculo() != null ? atencion.getCubiculo().getId() : "",
                    "duracion", atencion.getDuracionMinutosProtocolo() != null ? atencion.getDuracionMinutosProtocolo() : 0
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }



}
