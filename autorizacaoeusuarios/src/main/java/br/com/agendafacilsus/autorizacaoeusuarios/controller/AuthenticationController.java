package br.com.agendafacilsus.autorizacaoeusuarios.controller;


import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthenticationService;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthorizationService;
import br.com.agendafacilsus.commonlibrary.domains.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    @GetMapping("/ping")
    public String pong(){
        return "Pong " + UUID.randomUUID();
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

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){
        authorizationService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all-users")
    public ResponseEntity<Page<FetchUserDto>> getAllUsers (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(authorizationService.getAllUsers(page, size));
    }
}
