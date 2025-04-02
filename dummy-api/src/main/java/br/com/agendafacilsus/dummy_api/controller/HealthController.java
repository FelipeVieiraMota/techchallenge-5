package br.com.agendafacilsus.dummy_api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dummy-api")
public class HealthController {
    
    @GetMapping("/ping")
    @PreAuthorize("hasRole('PACIENTE')")
    public String pong(){
        return "pong";
    }

    @GetMapping("/protected-ping")
    @PreAuthorize("hasRole('PACIENTE')")
    public String adminProtected(){
        return "End point protected. Only patients has access. " + UUID.randomUUID().toString();
    }
}
