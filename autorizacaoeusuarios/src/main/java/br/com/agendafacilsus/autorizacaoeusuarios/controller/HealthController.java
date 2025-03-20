package br.com.agendafacilsus.autorizacaoeusuarios.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthController {
    
    @GetMapping("/ping")
    public String pong(){
        return "pong";
    }

    @GetMapping("admin-protected")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminProtected(){
        return "End point protected by credentials, only admin has access.";
    }
}
