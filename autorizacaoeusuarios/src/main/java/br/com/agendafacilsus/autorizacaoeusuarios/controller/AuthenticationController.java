package br.com.agendafacilsus.autorizacaoeusuarios.controller;


import br.com.agendafacilsus.autorizacaoeusuarios.domains.dto.AuthenticationDto;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.dto.LoginResponseDto;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.dto.RegisterDto;
import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.security.TokenService;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.agendafacilsus.autorizacaoeusuarios.domains.enums.UserRole.ADMIN;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    // TODO: Precisa realmente validar o token....
    @PostMapping("/validation")
    public ResponseEntity<User> tokenValidation() {
        var user = new User("b1a5f58e-8af5-4b67-bbdc-61c964c4507d", "admin", "$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a", ADMIN);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDto data){
        var newUser = authorizationService.register(data);
        return ResponseEntity.ok().body(newUser);
    }
}
