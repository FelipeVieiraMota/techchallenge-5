package br.com.agendafacilsus.agendamentos.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(example = """
        {
          "medicoId": "1",
          "data": "2025-04-13",
          "horarios": ["08:00", "09:30"]
        }
        """)
public record HorarioDisponivelDTO(
        String medicoId,
        LocalDate data,
        List<String> horarios
) {
}
