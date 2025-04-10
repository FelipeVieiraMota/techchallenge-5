package br.com.agendafacilsus.exameseconsultas.infrastructure.mapper;

import br.com.agendafacilsus.exameseconsultas.domain.model.Especialidade;
import br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto.EspecialidadeResponseDTO;

public class EspecialidadeMapper {

    private EspecialidadeMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Especialidade toEntity(EspecialidadeRequestDTO dto) {
        return new Especialidade(
                null, // ID será gerado no banco
                dto.descricao(),
                dto.tipoEspecialidade()
        );
    }

    public static EspecialidadeResponseDTO toResponseDTO(Especialidade especialidade) {
        return new EspecialidadeResponseDTO(
                especialidade.getId(),
                especialidade.getDescricao(),
                especialidade.getTipoEspecialidade()
        );
    }
}
