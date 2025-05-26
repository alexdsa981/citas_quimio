package com.ipor.quimioterapia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {


    //redirige / a /login
    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }


    // Método para manejar la vista de inicio y mostrar los tickets
    @GetMapping("/inicio")
    public String redirigePaginaInicio(Model model) {
        model.addAttribute("Titulo", "Quimioterapia | Principal");
        return "index";
    }



}
