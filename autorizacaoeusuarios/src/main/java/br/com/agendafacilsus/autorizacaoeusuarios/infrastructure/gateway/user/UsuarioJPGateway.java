package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.user;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UsuarioJPGateway {
    User salvar(User usuario);
    Optional<User> buscarPorId(String id);
    List<User> listar();
    void excluir(String id);
}