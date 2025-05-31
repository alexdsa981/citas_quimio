package com.ipor.quimioterapia.controller.other;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalAdviceController {

    @ModelAttribute
    public void datosTiempo(Model model) {
        LocalDateTime now = LocalDateTime.now(); // Fecha y hora actual

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateInputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Para input type="date"

        model.addAttribute("horaActual", now.format(timeFormatter));
        model.addAttribute("fechaActual", now.format(dateFormatter));
        model.addAttribute("fechaInputActual", now.format(dateInputFormatter)); // Este lo usas en el input
    }
}

