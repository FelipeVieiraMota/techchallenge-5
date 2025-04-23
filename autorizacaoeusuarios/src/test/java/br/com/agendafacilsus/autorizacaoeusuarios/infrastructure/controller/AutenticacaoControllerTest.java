package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.controller;

import br.com.agendafacilsus.autorizacaoeusuarios.application.usecase.AutenticacaoUseCase;
import br.com.agendafacilsus.commonlibrary.domain.dto.LoginDto;
import br.com.agendafacilsus.commonlibrary.domain.dto.LoginResponseDto;
import br.com.agendafacilsus.commonlibrary.domain.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutenticacaoUseCase autenticacaoUseCase;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveRealizarLoginComSucesso() throws Exception {
        LoginDto loginDto = new LoginDto("usuario@email.com", "senha123");
        LoginResponseDto loginResponseDto = new LoginResponseDto("token.jwt.aqui");

        Mockito.when(autenticacaoUseCase.autenticar(loginDto)).thenReturn("token.jwt.aqui");

        mockMvc.perform(post("/autenticacao/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("token.jwt.aqui")));
    }

    @Test
    void deveValidarTokenComSucesso() throws Exception {
        TokenDto tokenDto = new TokenDto("token.jwt");

        Mockito.when(autenticacaoUseCase.validarToken(tokenDto)).thenReturn(true);

        mockMvc.perform(post("/autenticacao/validar-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
