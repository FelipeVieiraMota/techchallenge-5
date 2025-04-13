package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.AgendamentoUseCase;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AlteracaoStatusRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoUseCase useCase;

    @Operation(summary = "Criar um novo agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content)
    })
    @PreAuthorize("hasAnyRole('PACIENTE', 'ADMIN')")
    @PostMapping
    public AgendamentoResponseDTO criarAgendamento(@Valid @RequestBody AgendamentoRequestDTO dto) {
        return useCase.criar(dto);
    }

    @Operation(summary = "Listar todos os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        return useCase.listar();
    }

    @Operation(summary = "Buscar um agendamento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content)
    })
    @PreAuthorize("hasAnyRole('PACIENTE', 'MEDICO', 'ADMIN')")
    @GetMapping("/{id}")
    public AgendamentoResponseDTO buscarAgendamentoPorId(@PathVariable Long id) {
        return useCase.buscarPorId(id);
    }

    @Operation(summary = "Atualizar um agendamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content)
    })
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    @PutMapping("/{id}")
    public AgendamentoResponseDTO atualizarAgendamento(@PathVariable Long id,
                                                       @Valid @RequestBody AgendamentoRequestDTO dto) {
        return useCase.atualizar(id, dto);
    }

    @Operation(summary = "Alterar o status de um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status alterado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content)
    })
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    @PatchMapping("/{id}/status")
    public AgendamentoResponseDTO alterarStatusAgendamento(@PathVariable Long id,
                                                           @Valid @RequestBody AlteracaoStatusRequestDTO requestDTO) {
        return useCase.alterarStatus(id, requestDTO.novoStatus());
    }

    @Operation(summary = "Excluir um agendamento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    @DeleteMapping("/{id}")
    public void excluirAgendamento(@PathVariable Long id) {
        useCase.excluir(id);
    }
}
