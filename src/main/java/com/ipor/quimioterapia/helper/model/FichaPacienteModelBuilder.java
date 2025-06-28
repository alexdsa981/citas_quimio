package com.ipor.quimioterapia.helper.model;

import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPaciente;
import com.ipor.quimioterapia.gestioncitas.fichapaciente.FichaPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class FichaPacienteModelBuilder {
    @Autowired
    FichaPacienteService fichaPacienteService;
    public Model getListaFichasPacienteDelDia(Model model) {
        List<FichaPaciente> lista = fichaPacienteService.getLista();
        model.addAttribute("Lista_FichasPaciente", lista);
        return model;
    }
}
