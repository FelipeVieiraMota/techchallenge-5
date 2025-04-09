package br.com.agendafacilsus.exames.infrastructure.controller;

import br.com.agendafacilsus.exames.infrastructure.controller.dto.ExameRequestDTO;
import br.com.agendafacilsus.exames.infrastructure.controller.dto.ExameResponseDTO;
import br.com.agendafacilsus.exames.application.usecase.ExameUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
public class ExameController {

    private final ExameUseCase useCase;

    public ExameController(ExameUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<ExameResponseDTO> criar(@RequestBody @Valid ExameRequestDTO dto) {
        return ResponseEntity.ok(useCase.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ExameResponseDTO>> listarConsultas() {
        return ResponseEntity.ok(useCase.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameResponseDTO> atualizar(@PathVariable Long id,
                                                      @RequestBody @Valid ExameRequestDTO dto) {
        return ResponseEntity.ok(useCase.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
