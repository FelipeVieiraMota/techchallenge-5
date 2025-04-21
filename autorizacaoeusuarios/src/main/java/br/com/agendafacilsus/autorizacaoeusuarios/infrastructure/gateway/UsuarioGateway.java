package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(String id);
    List<Usuario> listar();
    void excluir(String id);
    UserDetails buscarPorLogin(String token);
    Page<Usuario> buscarPorPapel(UserRole role, int page, int size);
}
