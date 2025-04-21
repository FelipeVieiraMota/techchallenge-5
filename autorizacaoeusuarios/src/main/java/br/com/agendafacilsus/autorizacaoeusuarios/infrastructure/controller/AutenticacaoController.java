package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.controller;

import br.com.agendafacilsus.autorizacaoeusuarios.application.usecase.AutenticacaoUseCase;
import br.com.agendafacilsus.commonlibrary.domain.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoUseCase autenticacaoUseCase;

    @Operation(summary = "Realiza o login do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto data) {
        return ResponseEntity.ok(new LoginResponseDto(autenticacaoUseCase.autenticar(data)));
    }

    @Operation(summary = "Valida o token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Token inválido", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/validar-token")
    public ResponseEntity<Boolean> validarToken(@RequestBody TokenDto data) {
        return ResponseEntity.ok(autenticacaoUseCase.validarToken(data));
    }
}
