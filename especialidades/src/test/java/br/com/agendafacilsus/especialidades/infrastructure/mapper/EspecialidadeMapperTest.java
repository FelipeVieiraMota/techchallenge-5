package br.com.agendafacilsus.especialidades.infrastructure.mapper;

import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EspecialidadeMapperTest {

    @Test
    public void testToEntity() {
        EspecialidadeRequestDTO dto = new EspecialidadeRequestDTO("Ortopedia", TipoEspecialidade.EXAME);

        Especialidade especialidade = EspecialidadeMapper.toEntity(dto);

        assertNotNull(especialidade);
        assertEquals(dto.descricao(), especialidade.getDescricao());
        assertEquals(dto.tipoEspecialidade(), especialidade.getTipoEspecialidade());
    }

    @Test
    public void testToResponseDTO() {
        Especialidade especialidade = Especialidade.builder()
                .id(100L)
                .descricao("Ortopedia")
                .tipoEspecialidade(TipoEspecialidade.EXAME)
                .build();

        EspecialidadeResponseDTO responseDTO = EspecialidadeMapper.toResponseDTO(especialidade);

        assertNotNull(responseDTO);
        assertEquals(especialidade.getId(), responseDTO.id());
        assertEquals(especialidade.getDescricao(), responseDTO.descricao());
        assertEquals(especialidade.getTipoEspecialidade(), responseDTO.especialidade());
    }
}
