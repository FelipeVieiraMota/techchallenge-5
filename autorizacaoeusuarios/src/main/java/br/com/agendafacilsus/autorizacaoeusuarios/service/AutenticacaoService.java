package br.com.agendafacilsus.autorizacaoeusuarios.service;

import br.com.agendafacilsus.commonlibrary.domain.exception.UsuarioNaoEncontradoException;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioGateway usuarioGateway;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsuarioNaoEncontradoException {
        return usuarioGateway.buscarPorLogin(username);
    }
}

