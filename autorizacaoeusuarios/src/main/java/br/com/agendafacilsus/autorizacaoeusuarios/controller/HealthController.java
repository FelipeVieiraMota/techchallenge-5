package br.com.agendafacilsus.autorizacaoeusuarios.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping
public class HealthController {
    
    @GetMapping("/ping")
    public String pong(){
        return "pong";
    }

    @GetMapping("/protected-ping")
    @PreAuthorize("hasRole('PACIENTE')")
    public String adminProtected(){
        return "End point protected. Only pacients has access. " + UUID.randomUUID().toString();
    }
}
