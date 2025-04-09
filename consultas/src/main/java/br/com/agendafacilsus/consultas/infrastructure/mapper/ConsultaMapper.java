package br.com.agendafacilsus.consultas.infrastructure.mapper;

import br.com.agendafacilsus.consultas.infrastructure.controller.dto.ConsultaRequestDTO;
import br.com.agendafacilsus.consultas.infrastructure.controller.dto.ConsultaResponseDTO;
import br.com.agendafacilsus.consultas.domain.model.Consulta;

public class ConsultaMapper {

    private ConsultaMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Consulta toEntity(ConsultaRequestDTO dto) {
        return new Consulta(
                null, // ID será gerado no banco
                dto.especialidade()
        );
    }

    public static ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getEspecialidade()
        );
    }
}
