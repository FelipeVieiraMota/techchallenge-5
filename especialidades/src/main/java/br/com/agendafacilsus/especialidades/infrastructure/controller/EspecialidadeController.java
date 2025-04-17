package br.com.agendafacilsus.especialidades.infrastructure.controller;

import br.com.agendafacilsus.especialidades.applicaton.usecase.EspecialidadeUseCase;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeUseCase useCase;

    public EspecialidadeController(EspecialidadeUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Criar uma nova especialidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade criada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadeResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão para acessar o recurso", content = @Content)
    })
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> criarEspecialidade(@Valid @RequestBody EspecialidadeRequestDTO dto) {
        return ResponseEntity.ok(useCase.criar(dto));
    }

    @Operation(summary = "Listar todas as especialidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadeResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão para acessar o recurso", content = @Content)
    })
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listarEspecialidades() {
        return ResponseEntity.ok(useCase.listar());
    }

    @Operation(summary = "Buscar uma especialidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade encontrada",
                    content = @Content(schema = @Schema(implementation = EspecialidadeResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão para acessar o recurso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada", content = @Content)
    })
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarEspecialidadePorId(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.buscarPorId(id));
    }

    @Operation(summary = "Atualizar uma especialidade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadeResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão para acessar o recurso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada", content = @Content)
    })
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizarEspecialidade(@PathVariable Long id,
                                                                           @Valid @RequestBody EspecialidadeRequestDTO dto) {
        return ResponseEntity.ok(useCase.atualizar(id, dto));
    }

    @Operation(summary = "Excluir uma especialidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Especialidade excluída com sucesso", content = @Content),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão para acessar o recurso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada", content = @Content)
    })
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEspecialidade(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
