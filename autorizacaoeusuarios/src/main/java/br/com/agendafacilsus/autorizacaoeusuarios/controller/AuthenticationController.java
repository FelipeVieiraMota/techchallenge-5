package br.com.agendafacilsus.autorizacaoeusuarios.controller;


import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthenticationService;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthorizationService;
import br.com.agendafacilsus.commonlibrary.domains.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthenticationDto data){
        final var token = authenticationService.login(data);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/validation")
    public ResponseEntity<Boolean> tokenValidation(@RequestBody TokenDto data) {

        if(authenticationService.validateToken(data)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDto data){
        return ResponseEntity.ok().body(authorizationService.register(data));
    }
}
