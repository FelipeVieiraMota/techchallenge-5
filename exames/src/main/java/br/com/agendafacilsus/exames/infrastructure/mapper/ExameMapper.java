package br.com.agendafacilsus.exames.infrastructure.mapper;

import br.com.agendafacilsus.exames.infrastructure.controller.dto.ExameRequestDTO;
import br.com.agendafacilsus.exames.infrastructure.controller.dto.ExameResponseDTO;
import br.com.agendafacilsus.exames.domain.model.Exame;

public class ExameMapper {

    private ExameMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Exame toEntity(ExameRequestDTO dto) {
        return new Exame(
                null, //ID gerado pelo banco
                dto.tipoExame()
        );
    }

    public static ExameResponseDTO toResponseDTO(Exame exame) {
        return new ExameResponseDTO(
                exame.getId(),
                exame.getTipoExame()
        );
    }
}
