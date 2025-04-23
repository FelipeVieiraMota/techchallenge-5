package br.com.agendafacilsus.especialidades.infrastructure.repository;

import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EspecialidadeRepositoryTest {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    private Especialidade especialidade;

    @BeforeEach
    void setUp() {
        especialidade = Especialidade.builder()
                .descricao("Ortopedia")
                .tipoEspecialidade(TipoEspecialidade.CONSULTA)
                .build();

        especialidadeRepository.save(especialidade);
    }

    @Test
    void testExistsByDescricaoIgnoreCaseExactMatch() {
        boolean exists = especialidadeRepository.existsByDescricaoIgnoreCase("Ortopedia");
        assertTrue(exists, "Deve retornar verdadeiro para correspondência exata de descrição");
    }

    @Test
    void testExistsByDescricaoIgnoreCaseDifferentCase() {
        boolean exists = especialidadeRepository.existsByDescricaoIgnoreCase("ortopedia");
        assertTrue(exists, "Deve retornar verdadeiro ignorando letras maiúsculas/minúsculas");
    }

    @Test
    void testDoesNotExistByDescricaoIgnoreCase() {
        boolean exists = especialidadeRepository.existsByDescricaoIgnoreCase("Neurologia");
        assertFalse(exists, "Deve retornar falso para descrição inexistente");
    }
}
