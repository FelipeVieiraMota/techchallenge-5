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
    public String pong(){
        return "pong";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('ADMIN')")
    public String privateEndPoint(){
        return "This is a private URI : " + UUID.randomUUID().toString();
    }
}
