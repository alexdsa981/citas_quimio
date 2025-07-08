package com.ipor.quimioterapia.gestioncitas.fichapaciente.funcionesvitales;


import com.ipor.quimioterapia.gestioncitas.dto.FuncionesVitalesDTO;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/app/funciones-vitales")
public class FuncionesVitalesController {

    @Autowired
    FichaPacienteService fichaPacienteService;
    @Autowired
    FuncionesVitalesService funcionesVitalesService;

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarSignosVitales(@RequestBody FuncionesVitalesDTO dto) {
        FichaPaciente fichaPaciente = fichaPacienteService.getPorID(dto.getIdFicha());
        funcionesVitalesService.setDatoAFicha(dto, fichaPaciente);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Signos vitales guardados correctamente");
        return ResponseEntity.ok(response);
    }


}
