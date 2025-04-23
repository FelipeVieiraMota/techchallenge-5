package br.com.agendafacilsus.especialidades.application.usecase;

import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import br.com.agendafacilsus.commonlibrary.domain.exception.EspecialidadeNaoEncontradaException;
import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.especialidades.applicaton.usecase.EspecialidadeUseCase;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.especialidades.infrastructure.gateway.EspecialidadeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EspecialidadeUseCaseTest {

    private EspecialidadeGateway gateway;
    private EspecialidadeUseCase useCase;

    @BeforeEach
    void setUp() {
        gateway = mock(EspecialidadeGateway.class);
        useCase = new EspecialidadeUseCase(gateway);
    }

    @Test
    void deveCriarEspecialidade() {
        var dto = new EspecialidadeRequestDTO("Cardiologia", TipoEspecialidade.CONSULTA);
        var entity = Especialidade.builder()
                .id(1L)
                .descricao("Cardiologia")
                .tipoEspecialidade(TipoEspecialidade.CONSULTA)
                .build();

        when(gateway.salvar(any())).thenReturn(entity);

        var response = useCase.criar(dto);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Cardiologia", response.descricao());
        assertEquals(TipoEspecialidade.CONSULTA, response.especialidade());

        verify(gateway).salvar(any());
    }

    @Test
    void deveListarEspecialidades() {
        var lista = List.of(
                new Especialidade(1L, "Cardiologia", TipoEspecialidade.CONSULTA),
                new Especialidade(2L, "Raio-X", TipoEspecialidade.EXAME)
        );

        when(gateway.listar()).thenReturn(lista);

        var result = useCase.listar();

        assertEquals(2, result.size());
        assertEquals("Cardiologia", result.get(0).descricao());
        assertEquals("Raio-X", result.get(1).descricao());

        verify(gateway).listar();
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        var especialidade = new Especialidade(1L, "Cardiologia", TipoEspecialidade.CONSULTA);

        when(gateway.buscarPorId(1L)).thenReturn(Optional.of(especialidade));

        var response = useCase.buscarPorId(1L);

        assertEquals(1L, response.id());
        assertEquals("Cardiologia", response.descricao());
        assertEquals(TipoEspecialidade.CONSULTA, response.especialidade());

        verify(gateway).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        when(gateway.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(EspecialidadeNaoEncontradaException.class, () -> useCase.buscarPorId(99L));
    }

    @Test
    void deveAtualizarEspecialidade() {
        var dto = new EspecialidadeRequestDTO("Gastroenterologia", TipoEspecialidade.CONSULTA);
        var entityAtualizada = new Especialidade(1L, "Gastroenterologia", TipoEspecialidade.CONSULTA);

        when(gateway.buscarPorId(1L)).thenReturn(Optional.of(new Especialidade()));
        when(gateway.salvar(any())).thenReturn(entityAtualizada);

        var response = useCase.atualizar(1L, dto);

        assertEquals(1L, response.id());
        assertEquals("Gastroenterologia", response.descricao());

        ArgumentCaptor<Especialidade> captor = ArgumentCaptor.forClass(Especialidade.class);
        verify(gateway).salvar(captor.capture());
        assertEquals(1L, captor.getValue().getId());
    }

    @Test
    void deveLancarExcecaoAoAtualizarEspecialidadeInexistente() {
        var dto = new EspecialidadeRequestDTO("Neurologia", TipoEspecialidade.CONSULTA);

        when(gateway.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(EspecialidadeNaoEncontradaException.class, () -> useCase.atualizar(99L, dto));
    }

    @Test
    void deveExcluirEspecialidade() {
        when(gateway.buscarPorId(1L)).thenReturn(Optional.of(new Especialidade()));

        useCase.excluir(1L);

        verify(gateway).excluir(1L);
    }

    @Test
    void deveLancarExcecaoAoExcluirEspecialidadeInexistente() {
        when(gateway.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(EspecialidadeNaoEncontradaException.class, () -> useCase.excluir(99L));
    }
}
