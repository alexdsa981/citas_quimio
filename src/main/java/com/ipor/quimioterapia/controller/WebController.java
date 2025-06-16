package com.ipor.quimioterapia.controller;

import com.ipor.quimioterapia.helper.model.ClasificadoresModelBuilder;
import com.ipor.quimioterapia.helper.model.FichaPacienteModelBuilder;
import com.ipor.quimioterapia.helper.model.PersonalModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    ClasificadoresModelBuilder clasificadoresModelBuilder;
    @Autowired
    PersonalModelBuilder personalModelBuilder;
    @Autowired
    FichaPacienteModelBuilder fichaPacienteModelBuilder;

    //redirige / a /login
    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }


    // Método para manejar la vista de inicio y mostrar los tickets
    @GetMapping("/inicio")
    public String redirigePaginaInicio(Model model) {
        clasificadoresModelBuilder.getListaEstadoCita(model);
        clasificadoresModelBuilder.getListaCieActivos(model);
        clasificadoresModelBuilder.getListaCubiculosActivos(model);



        personalModelBuilder.getListaMedicosActivos(model);
        personalModelBuilder.getListaEnfermerasActivos(model);

        fichaPacienteModelBuilder.getListaFichasPacienteDelDia(model);

        model.addAttribute("Titulo", "Quimioterapia | Principal");
        return "index";
    }



}
