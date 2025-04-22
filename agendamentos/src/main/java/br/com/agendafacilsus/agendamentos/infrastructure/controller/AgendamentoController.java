package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.AgendamentoUseCase;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AlteracaoStatusRequestDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
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
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content),
            @ApiResponse(responseCode = "409", description = "Agendamento já cadastrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasAnyRole('PACIENTE', 'ADMIN')")
    @PostMapping
    public AgendamentoResponseDTO criarAgendamento(@Valid @RequestBody AgendamentoRequestDTO dto, HttpServletRequest request) {
        String tokenJWT = recuperarToken(request);
        return useCase.criar(dto, tokenJWT);
    }

    @Operation(summary = "Listar todos os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
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
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
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
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    @PutMapping("/{id}")
    public AgendamentoResponseDTO atualizarAgendamento(@PathVariable Long id, HttpServletRequest request,
                                                       @Valid @RequestBody AgendamentoRequestDTO dto) {
        String tokenJWT = recuperarToken(request);
        return useCase.atualizar(id, dto, tokenJWT);
    }

    @Operation(summary = "Alterar o status de um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status alterado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
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
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    @DeleteMapping("/{id}")
    public void excluirAgendamento(@PathVariable Long id) {
        useCase.excluir(id);
    }

    /**
     * Endpoint para buscar a especialidade
     * @param especialidadeId ID da especialidade
     * @return EspecialidadeResponseDTO
     */
    @Operation(summary = "Buscar Especialidade em outro micro-serviço", description = "Recupera a especialidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade encontrada"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    @GetMapping("/especialidade/{especialidadeId}")
    public EspecialidadeResponseDTO getEspecialidade(@PathVariable Long especialidadeId, HttpServletRequest request) {
        String tokenJWT = recuperarToken(request);
        return useCase.buscarEspecialidade(especialidadeId, tokenJWT);
    }

    /**
     * Endpoint para buscar o usuário
     * @param usuarioId ID do usuário
     * @return UsuarioResponseDTO
     */
    @Operation(summary = "Buscar Usuário em outro micro-serviço", description = "Recupera o usuário com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}")
    public UsuarioResponseDTO getUsuario(@PathVariable String usuarioId, HttpServletRequest request) {
        String tokenJWT = recuperarToken(request);
        return useCase.buscarUsuario(usuarioId, tokenJWT);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remover o prefixo "Bearer " do token
        }
        return null; // Ou lançar exceção caso o token não esteja presente
    }
}

