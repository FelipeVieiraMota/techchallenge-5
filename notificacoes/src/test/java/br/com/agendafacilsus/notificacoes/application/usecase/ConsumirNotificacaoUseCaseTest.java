package br.com.agendafacilsus.notificacoes.application.usecase;

import br.com.agendafacilsus.notificacoes.application.usecase.ConsumirNotificacaoUseCase;
import br.com.agendafacilsus.notificacoes.infrastructure.dto.NotificacaoDTO;
import br.com.agendafacilsus.notificacoes.domain.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.application.usecase.rabbitmq.ProcessarFila;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ConsumirNotificacaoUseCaseTest {

    @Mock
    private ProcessarFila processarFila;

    @InjectMocks
    private ConsumirNotificacaoUseCase consumirNotificacaoUseCase;

    private NotificacaoDTO notificacaoDTO;

    @BeforeEach
    public void setUp() {
        notificacaoDTO = new NotificacaoDTO("Teste", "16997609550", "Mensagem de teste", TipoNotificacao.SMS);
    }

    @Test
    void testReceberNotificacao() {
        //
        Consumer<NotificacaoDTO> consumer = consumirNotificacaoUseCase.receber();

        //
        consumer.accept(notificacaoDTO);

        //
        verify(processarFila, times(1)).gerenciarNotificacoes(eq(TipoNotificacao.SMS), eq(notificacaoDTO));
    }
}
