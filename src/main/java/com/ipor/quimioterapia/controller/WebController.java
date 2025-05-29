package com.ipor.quimioterapia.controller;

import com.ipor.quimioterapia.helper.model.ClasificadoresModelBuilder;
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
    //redirige / a /login
    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }


    // Método para manejar la vista de inicio y mostrar los tickets
    @GetMapping("/inicio")
    public String redirigePaginaInicio(Model model) {
        clasificadoresModelBuilder.getListaEstadoCita(model);
        clasificadoresModelBuilder.getListaAseguradorasActivos(model);
        clasificadoresModelBuilder.getListaCieActivos(model);
        clasificadoresModelBuilder.getListaContratantesActivos(model);
        clasificadoresModelBuilder.getListaCubiculosActivos(model);
        clasificadoresModelBuilder.getListaTipoDocIdentidadActivos(model);
        clasificadoresModelBuilder.getListaTipoPacienteActivos(model);

        personalModelBuilder.getListaMedicosActivos(model);
        personalModelBuilder.getListaEnfermerasActivos(model);

        model.addAttribute("Titulo", "Quimioterapia | Principal");
        return "index";
    }



}
