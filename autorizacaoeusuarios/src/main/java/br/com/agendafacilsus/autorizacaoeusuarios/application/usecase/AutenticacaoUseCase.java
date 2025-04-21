package br.com.agendafacilsus.autorizacaoeusuarios.application.usecase;

import br.com.agendafacilsus.commonlibrary.domain.dto.LoginDto;
import br.com.agendafacilsus.commonlibrary.domain.dto.TokenDto;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import br.com.agendafacilsus.commonlibrary.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutenticacaoUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Value("${api.security.token.secret}")
    private String secret;

    public String autenticar(LoginDto data) {
        val usuarioSenha = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        val autenticacao = authenticationManager.authenticate(usuarioSenha);
        val usuario = (Usuario) autenticacao.getPrincipal();
        return tokenService.generateToken(secret, usuario);
    }

    public boolean validarToken(TokenDto data) {
        return tokenService.validateToken(secret, data.token());
    }
}

