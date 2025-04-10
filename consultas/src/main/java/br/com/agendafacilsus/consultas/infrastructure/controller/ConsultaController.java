package br.com.agendafacilsus.consultas.infrastructure.controller;

import br.com.agendafacilsus.consultas.infrastructure.controller.dto.ConsultaRequestDTO;
import br.com.agendafacilsus.consultas.infrastructure.controller.dto.ConsultaResponseDTO;
import br.com.agendafacilsus.consultas.applicaton.usecase.ConsultaUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaUseCase useCase;

    public ConsultaController(ConsultaUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/ping")
    @PreAuthorize("hasRole('PACIENTE')")
    public String pong(){
        return "Pong " + UUID.randomUUID();
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> criar(@RequestBody @Valid ConsultaRequestDTO dto) {
        return ResponseEntity.ok(useCase.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultas() {
        return ResponseEntity.ok(useCase.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> atualizar(@PathVariable Long id,
                                                         @RequestBody @Valid ConsultaRequestDTO dto) {
        return ResponseEntity.ok(useCase.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
