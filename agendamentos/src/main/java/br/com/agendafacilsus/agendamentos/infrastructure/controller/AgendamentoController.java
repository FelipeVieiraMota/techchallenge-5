package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.AgendamentoUseCase;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoRequestDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AgendamentoResponseDTO;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.AlteracaoStatusRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoUseCase useCase;

    @PreAuthorize("hasRole('PACIENTE', 'ADMIN')")
    @PostMapping
    public AgendamentoResponseDTO criar(@RequestBody AgendamentoRequestDTO dto) {
        return useCase.criar(dto);
    }

    //Fazer tratativa de: nomePaciente e médico só pode listar se estiverem vinculados aos agendamentos
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        return useCase.listar();
    }

    @PreAuthorize("hasAnyRole('PACIENTE', 'MEDICO', 'ADMIN')")
    @GetMapping("/{id}")
    public AgendamentoResponseDTO buscarPorId(@PathVariable Long id) {
        return useCase.buscarPorId(id);
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    @PutMapping("/{id}")
    public AgendamentoResponseDTO atualizar(@PathVariable Long id, @RequestBody AgendamentoRequestDTO dto) {
        return useCase.atualizar(id, dto);
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    @PatchMapping("/{id}/status")
    public AgendamentoResponseDTO alterarStatus(@PathVariable Long id, @RequestBody AlteracaoStatusRequestDTO requestDTO) {
        return useCase.alterarStatus(id, requestDTO.novoStatus());
    }

    @PreAuthorize("hasRole('ADMIN', 'PACIENTE')")
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        useCase.excluir(id);
    }
}
