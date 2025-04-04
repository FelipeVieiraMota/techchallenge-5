package br.com.agendafacilsus.autorizacaoeusuarios.controller;


import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthenticationService;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthorizationService;
import br.com.agendafacilsus.commonlibrary.domains.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    @GetMapping("/ping")
    public String pong(){
        return "pong";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthenticationDto data){
        return ResponseEntity.ok(new LoginResponseDto(authenticationService.login(data)));
    }

    @PostMapping("/validation")
    public ResponseEntity<Boolean> tokenValidation(@RequestBody TokenDto data) {

        if(authenticationService.validateToken(data)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDto data){
        return ResponseEntity.ok().body(authorizationService.register(data));
    }
}
