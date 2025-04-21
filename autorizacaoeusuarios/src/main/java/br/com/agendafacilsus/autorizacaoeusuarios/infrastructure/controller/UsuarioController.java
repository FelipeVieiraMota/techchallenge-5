package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.controller;

import br.com.agendafacilsus.autorizacaoeusuarios.application.usecase.UsuarioUseCase;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.ListaUsuariosResponseDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioRequestDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    public UsuarioController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @Operation(summary = "Registra um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/criar")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO data) {
        return ResponseEntity.ok().body(usuarioUseCase.criar(data));
    }

    @Operation(summary = "Atualizar um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão para acessar o recurso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable String id,
                                                               @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioUseCase.atualizar(id, dto));
    }

    @Operation(summary = "Exclui um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String id) {
        usuarioUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todos-usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosUsuarios() {
        return ResponseEntity.ok(usuarioUseCase.listar());
    }

    @Operation(summary = "Lista todos os pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = ListaUsuariosResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todos-pacientes")
    public ResponseEntity<ListaUsuariosResponseDTO> listarTodosPacientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UsuarioResponseDTO> pagina = usuarioUseCase.buscarPorPapel(UserRole.PACIENTE, page, size);
        return ResponseEntity.ok(new ListaUsuariosResponseDTO(pagina.getTotalElements(), pagina.getContent()));
    }

    @Operation(summary = "Lista todos os médicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de médicos recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todos-medicos")
    public ResponseEntity<ListaUsuariosResponseDTO> listarTodosMedicos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UsuarioResponseDTO> pagina = usuarioUseCase.buscarPorPapel(UserRole.MEDICO, page, size);
        return ResponseEntity.ok(new ListaUsuariosResponseDTO(pagina.getTotalElements(), pagina.getContent()));
    }

    @Operation(summary = "Busca um usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable String id) {
        return ResponseEntity.ok(usuarioUseCase.buscarPorId(id));
    }
}
