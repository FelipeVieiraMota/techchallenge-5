package br.com.agendafacilsus.notificacoes.controller;

import br.com.agendafacilsus.notificacoes.enums.TipoNotificacao;
import br.com.agendafacilsus.notificacoes.usecase.EnviarNotificacaoUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificacaoController.class)
public class NotificacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnviarNotificacaoUseCase enviarNotificacaoUseCase;

    private NotificacaoDTO notificacaoDTO;
    private String notificacaoJSON;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        notificacaoDTO = new NotificacaoDTO("Teste", "16997609550", "Mensagem de teste", TipoNotificacao.SMS);
        notificacaoJSON = formataNotificacaoparaJSON(notificacaoDTO);
    }

    private String formataNotificacaoparaJSON(NotificacaoDTO notificacaoDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(notificacaoDTO);
    }

    @Test
    void testEnviarNotificacao() throws Exception {
        doNothing().when(enviarNotificacaoUseCase).enviar(any(NotificacaoDTO.class));

        mockMvc.perform(post("/notificacoes/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notificacaoJSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Notificação enviada com sucesso!"));
    }

    @Test
    void testGetTotalMensagensEnviadas() throws Exception {
        when(enviarNotificacaoUseCase.getTotalNotificacoesEnviadas(anyString())).thenReturn(5);

        mockMvc.perform(get("/notificacoes/QtdNotificacoesEnviadas"))
                .andExpect(status().isOk())
                .andExpect(content().string("Total de notificações enviadas: 5"));
    }
}
