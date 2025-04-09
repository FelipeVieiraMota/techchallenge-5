package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.AgendamentoUseCase;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AlteracaoStatusRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoUseCase useCase;

    @GetMapping("/ping")
    @PreAuthorize("hasRole('PACIENTE')")
    public String pong(){
        return "Pong " + UUID.randomUUID();
    }

    @PostMapping
    public AgendamentoResponseDTO criar(@RequestBody AgendamentoRequestDTO dto) {
        return useCase.criar(dto);
    }

    @GetMapping
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        return useCase.listar();
    }

    @GetMapping("/{id}")
    public AgendamentoResponseDTO buscarPorId(@PathVariable Long id) {
        return useCase.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AgendamentoResponseDTO atualizar(@PathVariable Long id, @RequestBody AgendamentoRequestDTO dto) {
        return useCase.atualizar(id, dto);
    }

    @PatchMapping("/{id}/status")
    public AgendamentoResponseDTO alterarStatus(@PathVariable Long id, @RequestBody AlteracaoStatusRequestDTO requestDTO) {
        return useCase.alterarStatus(id, requestDTO.novoStatus());
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        useCase.excluir(id);
    }
}