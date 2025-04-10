package br.com.agendafacilsus.notificacoes.application.usecase.rabbitmq;

import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ProcessarFilaTest {

    private ProcessarItemFila item1;
    private ProcessarItemFila item2;
    private ProcessarFila processarFila;

    private NotificacaoDTO notificacaoDTO;
    
    @BeforeEach
    void setUp() {
        item1 = mock(ProcessarItemFila.class);
        item2 = mock(ProcessarItemFila.class);

        processarFila = new ProcessarFila(List.of(item1, item2));
        notificacaoDTO = new NotificacaoDTO("Teste", "16997609550", "Mensagem de teste", TipoNotificacao.SMS);

    }

    @Test
    void deveProcessarNotificacaoQuandoTipoCorresponde() {
        //
        TipoNotificacao tipo = TipoNotificacao.SMS;

        when(item1.checaTipoNotificacao(tipo)).thenReturn(true);
        when(item2.checaTipoNotificacao(tipo)).thenReturn(false);

        //
        processarFila.gerenciarNotificacoes(tipo, notificacaoDTO);

        //
        verify(item1, times(1)).processar(notificacaoDTO);
        verify(item2, never()).processar(any());
    }

    @Test
    void naoDeveProcessarNenhumItemSeNenhumTipoCorresponder() {
        //
        TipoNotificacao tipo = TipoNotificacao.EMAIL;

        when(item1.checaTipoNotificacao(tipo)).thenReturn(false);
        when(item2.checaTipoNotificacao(tipo)).thenReturn(false);

        //
        processarFila.gerenciarNotificacoes(tipo, notificacaoDTO);

        //
        verify(item1, never()).processar(any());
        verify(item2, never()).processar(any());
    }
}
