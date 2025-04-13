package br.com.agendafacilsus.autorizacaoeusuarios.controller;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.controller.AuthenticationController;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthenticationService;
import br.com.agendafacilsus.autorizacaoeusuarios.service.AuthorizationService;
import br.com.agendafacilsus.commonlibrary.domains.dtos.*;
import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        authenticationService = mock(AuthenticationService.class);
        authorizationService = mock(AuthorizationService.class);
        authenticationController = new AuthenticationController(authenticationService, authorizationService);
    }


    @Test
    void deveRetornarPong() {
        String resposta = authenticationController.pong();
        assertTrue(resposta.startsWith("Pong "));
    }

    @Test
    void deveFazerLoginComSucesso() {
        AuthenticationDto authDto = new AuthenticationDto("user", "login", "pass");
        when(authenticationService.login(authDto)).thenReturn("mocked_token");

        ResponseEntity<LoginResponseDto> resposta = authenticationController.login(authDto);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("mocked_token", resposta.getBody().token());
    }

    @Test
    void deveValidarTokenComSucesso() {
        TokenDto tokenDto = new TokenDto("valid_token");
        when(authenticationService.validateToken(tokenDto)).thenReturn(true);

        ResponseEntity<Boolean> resposta = authenticationController.tokenValidation(tokenDto);

        assertTrue(resposta.getBody());
    }

    @Test
    void deveInvalidarToken() {
        TokenDto tokenDto = new TokenDto("invalid_token");
        when(authenticationService.validateToken(tokenDto)).thenReturn(false);

        ResponseEntity<Boolean> resposta = authenticationController.tokenValidation(tokenDto);

        assertFalse(resposta.getBody());
    }

    @Test
    void deveRegistrarUsuario() {
        RegisterDto registerDto = new RegisterDto("name", "email", "password", UserRole.ADMIN);
        User usuario = new User();
        when(authorizationService.register(registerDto)).thenReturn(usuario);

        ResponseEntity<User> resposta = authenticationController.register(registerDto);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(usuario, resposta.getBody());
    }

    @Test
    void deveDeletarUsuarioPorId() {
        String userId = "123";
        doNothing().when(authorizationService).deleteUserById(userId);

        ResponseEntity<Void> resposta = authenticationController.deleteUserById(userId);

        assertEquals(204, resposta.getStatusCodeValue());
        verify(authorizationService, times(1)).deleteUserById(userId);
    }

    @Test
    void deveRetornarTodosUsuarios() {
        Page<FetchUserDto> pagina = new PageImpl<>(Collections.emptyList());
        when(authorizationService.getAllUsers(0, 10)).thenReturn(pagina);

        ResponseEntity<Page<FetchUserDto>> resposta = authenticationController.getAllUsers(0, 10);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(pagina, resposta.getBody());
    }

    @Test
    void deveRetornarTodosPacientes() {
        Page<FetchUserDto> pagina = new PageImpl<>(Collections.emptyList());
        when(authorizationService.getAllPatients(0, 10)).thenReturn(pagina);

        ResponseEntity<Page<FetchUserDto>> resposta = authenticationController.getAllPatients(0, 10);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(pagina, resposta.getBody());
    }

    @Test
    void deveRetornarTodosMedicos() {
        Page<FetchUserDto> pagina = new PageImpl<>(Collections.emptyList());
        when(authorizationService.getAllDoctors(0, 10)).thenReturn(pagina);

        ResponseEntity<Page<FetchUserDto>> resposta = authenticationController.getAllDoctors(0, 10);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(pagina, resposta.getBody());
    }

    @Test
    void deveEncontrarUsuarioPorId() {
        String id = "abc123";
        FetchUserDto usuarioDto = new FetchUserDto("123", "name", "login", UserRole.PACIENTE);
        when(authorizationService.findUserById(id)).thenReturn(usuarioDto);

        ResponseEntity<FetchUserDto> resposta = authenticationController.findUserById(id);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(usuarioDto, resposta.getBody());
    }
}
