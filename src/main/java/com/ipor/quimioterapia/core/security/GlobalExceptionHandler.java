package com.ipor.quimioterapia.core.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UsernameNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("redirect:/login"); // Redirigir a login
        modelAndView.addObject("error", ex.getMessage()); // Si necesitas pasar un mensaje
        return modelAndView;
    }
}
