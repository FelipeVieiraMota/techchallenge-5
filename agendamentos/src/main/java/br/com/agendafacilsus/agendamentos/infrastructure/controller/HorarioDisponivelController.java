package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.HorarioDisponivelUseCase;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/horarios-disponiveis")
@RequiredArgsConstructor
public class HorarioDisponivelController {

    private final HorarioDisponivelUseCase horarioDisponivelUseCase;

    @Operation(summary = "Cadastrar horários disponíveis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horários cadastrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content),
            @ApiResponse(responseCode = "409", description = "Horários já cadastrados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Void> cadastrarHorarios(@Valid @RequestBody HorarioDisponivelDTO dto) {
        horarioDisponivelUseCase.cadastrarHorarios(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar horários disponíveis por médico e data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de horários recuperada com sucesso",
                    content = @Content(schema = @Schema(implementation = HorarioDisponivelDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissão", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasAnyRole('PACIENTE', 'MEDICO', 'ADMIN')")
    @GetMapping("/horarios")
    public ResponseEntity<List<HorarioDisponivelDTO>> listarHorarios(
            @RequestParam String medicoId,
            @RequestParam String data
    ) {
        return ResponseEntity.ok(horarioDisponivelUseCase.listarHorarios(medicoId, LocalDate.parse(data)));
    }

    @Operation(summary = "Excluir um horário disponível por médico, data e hora")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Horário excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "401", description = "Requisição sem autenticação válida", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário autenticado, mas sem permissão para acessar o recurso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Horário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    @DeleteMapping("/{medicoId}/{data}/{hora}")
    public void excluirHorario(@PathVariable String medicoId,
                                               @PathVariable String data,
                                               @PathVariable String hora) {
        horarioDisponivelUseCase.excluirHorario(medicoId, LocalDate.parse(data), LocalTime.parse(hora));
    }
}
