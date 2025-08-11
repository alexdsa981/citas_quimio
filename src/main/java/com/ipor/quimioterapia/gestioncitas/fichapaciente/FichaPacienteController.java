package com.ipor.quimioterapia.gestioncitas.fichapaciente;

import com.ipor.quimioterapia.gestioncitas.dto.FiltroFechasDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.registrosantiguos.RegistrosAntiguos;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.registrosantiguos.RegistrosAntiguosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/ficha-paciente")
public class FichaPacienteController {

    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    RegistrosAntiguosService registrosAntiguosService;

    @GetMapping("/registros-antiguos")
    public ResponseEntity<?> getRegistrosAntiguos(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        try {
            List<RegistrosAntiguos> fichas = registrosAntiguosService.registrosAntiguosEntreFechas(desde, hasta);
            return ResponseEntity.ok(fichas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al obtener fichas: " + e.getMessage()));
        }
    }



    @GetMapping("/fichas/hoy")
    public ResponseEntity<?> getFichasDelDia() {
        try {
            List<FichaPaciente> fichas = fichaPacienteService.getListaHoy();
            List<FichaPacienteDTO> fichasdto = new ArrayList<>();
            for (FichaPaciente ficha : fichas){
                FichaPacienteDTO dto = new FichaPacienteDTO(ficha);
                fichasdto.add(dto);
            }
            System.out.println(LocalDate.now() + " " + LocalTime.now());
            return ResponseEntity.ok(fichasdto);
        }  catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error al obtener fichas del día: " + e.getMessage()));
    }
    }

    @PostMapping("/fichas/entre-fechas")
    public ResponseEntity<?> getFichasEntreFechas(@RequestBody FiltroFechasDTO filtro) {
        try {
            List<FichaPaciente> fichasActuales = fichaPacienteService.getListaEntreFechas(filtro.getDesde(), filtro.getHasta());
            List<RegistrosAntiguos> fichasAntiguas = registrosAntiguosService.registrosAntiguosEntreFechas(filtro.getDesde(), filtro.getHasta());
            List<FichaPacienteDTO> listaDTO = new ArrayList<>();

            for (FichaPaciente ficha : fichasActuales) {
                listaDTO.add(new FichaPacienteDTO(ficha));
            }
            for (RegistrosAntiguos ficha : fichasAntiguas) {
                listaDTO.add(new FichaPacienteDTO(ficha));
            }

            // Ordenar por fecha ascendente
            listaDTO.sort(Comparator.comparing(FichaPacienteDTO::getCita_fecha));

            return ResponseEntity.ok(listaDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al obtener fichas entre fechas: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFichaPorId(@PathVariable Long id) {
        try {
            FichaPacienteDTO pacienteDTO;
            if (id > 0){
                FichaPaciente fp = fichaPacienteService.getPorID(id);
                pacienteDTO = new FichaPacienteDTO(fp);
            }else {
                RegistrosAntiguos ra = registrosAntiguosService.getPorID(-id);
                pacienteDTO = new FichaPacienteDTO(ra);
            }
            if (pacienteDTO.getFicha_id() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Ficha no encontrada con id " + id));
            }
            return ResponseEntity.ok(pacienteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al obtener ficha: " + e.getMessage()));
        }
    }







}
