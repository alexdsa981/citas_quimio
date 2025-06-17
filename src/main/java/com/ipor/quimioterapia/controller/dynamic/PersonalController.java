package com.ipor.quimioterapia.controller.dynamic;


import com.ipor.quimioterapia.model.dynamic.Enfermera;
import com.ipor.quimioterapia.model.dynamic.Medico;
import com.ipor.quimioterapia.model.other.DTO.EmpleadoCrearDTO;
import com.ipor.quimioterapia.repository.dynamic.EnfermeraRepository;
import com.ipor.quimioterapia.repository.dynamic.MedicoRepository;
import com.ipor.quimioterapia.service.dynamic.EnfermeraService;
import com.ipor.quimioterapia.service.dynamic.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/app/personal")
public class PersonalController {

    @Autowired
    private MedicoService medicoService;
    @Autowired
    private EnfermeraService enfermeraService;

    //MEDICOS----------------------------------
    @GetMapping("/medicos")
    public List<Medico> listarMedicos() {
        return medicoService.getListaActivos();
    }

    @PostMapping("/medico/guardar")
    public ResponseEntity<String> guardarMedico(@RequestBody EmpleadoCrearDTO dto) {
        medicoService.crearOActualizar(dto);
        return ResponseEntity.ok("Médico guardado correctamente");
    }

    @PostMapping("/medico/desactivar/{id}")
    public ResponseEntity<String> desactivarMedico(@PathVariable Long id) {
        medicoService.cambiarEstado(id, Boolean.FALSE);
        return ResponseEntity.ok("Médico desactivado correctamente");
    }



    //ENFERMERAS----------------------------------
    @GetMapping("/enfermeras")
    public List<Enfermera> listarEnfermeras() {
        return enfermeraService.getListaActivos();
    }

    @PostMapping("/enfermera/guardar")
    public ResponseEntity<String> guardarEnfermera(@RequestBody EmpleadoCrearDTO dto) {
        enfermeraService.crearOActualizar(dto);
        return ResponseEntity.ok("Enfermera guardada correctamente");
    }


    @PostMapping("/enfermera/desactivar/{id}")
    public ResponseEntity<String> desactivarEnfermera(@PathVariable Long id) {
        enfermeraService.cambiarEstado(id, Boolean.FALSE);
        return ResponseEntity.ok("Enfermera desactivada correctamente");
    }

}
