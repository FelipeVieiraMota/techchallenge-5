package br.com.agendafacilsus.exameseconsultas.infrastructure.controller;

import br.com.agendafacilsus.exameseconsultas.applicaton.usecase.EspecialidadeUseCase;
import br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto.EspecialidadeResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeUseCase useCase;

    public EspecialidadeController(EspecialidadeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/ping")
    @PreAuthorize("hasRole('PACIENTE')")
    public String pong(){
        return "Pong " + UUID.randomUUID();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> criar(@RequestBody @Valid EspecialidadeRequestDTO dto) {
        return ResponseEntity.ok(useCase.criar(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listarConsultas() {
        return ResponseEntity.ok(useCase.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizar(@PathVariable Long id,
                                                              @RequestBody @Valid EspecialidadeRequestDTO dto) {
        return ResponseEntity.ok(useCase.atualizar(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
