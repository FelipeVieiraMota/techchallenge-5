package br.com.agendafacilsus.agendamentos.infrastructure.controller;

import br.com.agendafacilsus.agendamentos.application.usecase.HorarioDisponivelUseCase;
import br.com.agendafacilsus.agendamentos.infrastructure.controller.dto.HorarioDisponivelDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/horarios-disponiveis")
@RequiredArgsConstructor
public class HorarioDisponivelController {

    private final HorarioDisponivelUseCase horarioDisponivelUseCase;

    @PostMapping
    public ResponseEntity<Void> cadastrarHorarios(@Valid @RequestBody HorarioDisponivelDTO dto) {
        horarioDisponivelUseCase.cadastrarHorarios(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/horarios")
    public ResponseEntity<List<HorarioDisponivelDTO>> listarHorarios(@RequestParam String medicoId, @RequestParam String data
    ) {
        return ResponseEntity.ok(horarioDisponivelUseCase.listarHorarios(medicoId, LocalDate.parse(data)));
    }
}