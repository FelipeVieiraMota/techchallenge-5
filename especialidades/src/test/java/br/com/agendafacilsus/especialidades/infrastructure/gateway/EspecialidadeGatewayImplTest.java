package br.com.agendafacilsus.especialidades.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.especialidades.exception.EspecialidadeDuplicadaException;
import br.com.agendafacilsus.especialidades.exception.EspecialidadeGatewayException;
import br.com.agendafacilsus.especialidades.infrastructure.repository.EspecialidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadeGatewayImplTest {

    @InjectMocks
    private EspecialidadeGatewayImpl especialidadeGatewayImpl;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    private Especialidade especialidade;

    @BeforeEach
    public void setUp() {
        especialidade = new Especialidade();
        especialidade.setId(1L);
        especialidade.setDescricao("Cardiologia");
    }

    @Test
    public void testSalvarEspecialidadeComSucesso() {
        when(especialidadeRepository.existsByDescricaoIgnoreCase(especialidade.getDescricao()))
                .thenReturn(false);
        when(especialidadeRepository.save(especialidade)).thenReturn(especialidade);

        Especialidade especialidadeSalva = especialidadeGatewayImpl.salvar(especialidade);

        assertNotNull(especialidadeSalva);
        assertEquals(especialidade, especialidadeSalva);
        verify(especialidadeRepository, times(1)).save(especialidade);
    }

    @Test
    public void testSalvarEspecialidadeDuplicada() {
        when(especialidadeRepository.existsByDescricaoIgnoreCase(especialidade.getDescricao()))
                .thenReturn(true);

        assertThrows(EspecialidadeDuplicadaException.class, () -> especialidadeGatewayImpl.salvar(especialidade));

        verify(especialidadeRepository, never()).save(especialidade);
    }

    @Test
    public void testSalvarEspecialidadeErro() {
        when(especialidadeRepository.existsByDescricaoIgnoreCase(especialidade.getDescricao()))
                .thenReturn(false);
        when(especialidadeRepository.save(especialidade)).thenThrow(new RuntimeException("Erro inesperado"));

        assertThrows(EspecialidadeGatewayException.class, () -> especialidadeGatewayImpl.salvar(especialidade));
    }

    @Test
    public void testListarEspecialidades() {
        when(especialidadeRepository.findAll()).thenReturn(List.of(especialidade));

        List<Especialidade> especialidades = especialidadeGatewayImpl.listar();

        assertNotNull(especialidades);
        assertFalse(especialidades.isEmpty());
        assertEquals(1, especialidades.size());
        assertEquals(especialidade, especialidades.get(0));
    }

    @Test
    public void testBuscarPorId() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.of(especialidade));

        Optional<Especialidade> especialidadeEncontrada = especialidadeGatewayImpl.buscarPorId(1L);

        assertTrue(especialidadeEncontrada.isPresent());
        assertEquals(especialidade, especialidadeEncontrada.get());
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        when(especialidadeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Especialidade> especialidadeEncontrada = especialidadeGatewayImpl.buscarPorId(1L);

        assertFalse(especialidadeEncontrada.isPresent());
    }

    @Test
    public void testExcluirEspecialidadeComSucesso() {
        doNothing().when(especialidadeRepository).deleteById(1L);

        especialidadeGatewayImpl.excluir(1L);

        verify(especialidadeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testExcluirEspecialidadeErro() {
        doThrow(new RuntimeException("Erro inesperado")).when(especialidadeRepository).deleteById(1L);

        assertThrows(EspecialidadeGatewayException.class, () -> especialidadeGatewayImpl.excluir(1L));
    }
}
