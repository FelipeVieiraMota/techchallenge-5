package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway;

import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioDuplicadoException;
import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioGatewayException;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.repository.UsuarioRepository;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private static final Logger logger = LogManager.getLogger(UsuarioGatewayImpl.class);

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) {
        try {
            val jaExisteUsuario = usuarioRepository.existsByLoginIgnoreCase(usuario.getLogin());

            if (jaExisteUsuario) {
                logger.warn("Tentativa de salvar usuário duplicado: {}", usuario);
                throw new UsuarioDuplicadoException();
            }

            val usuarioSalvo = usuarioRepository.save(usuario);
            logger.info("Usuário salvo com sucesso: {}", usuarioSalvo::toString);
            return usuarioSalvo;

        } catch (UsuarioDuplicadoException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao salvar usuário: {}", e.getMessage());
            throw new UsuarioGatewayException("Erro ao salvar usuário", e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public void excluir(String id) {
        try {
            usuarioRepository.deleteById(id);
            logger.info("Usuário com ID {} excluído com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir usuário: {}", e.getMessage());
            throw new UsuarioGatewayException("Erro ao excluir usuário", e);
        }
    }

    @Override
    public UserDetails buscarPorLogin(String token) {
        return usuarioRepository.findByLogin(token);
    }

    @Override
    public Page<Usuario> buscarPorPapel(UserRole role, int page, int size) {
        return usuarioRepository.findByRole(role, PageRequest.of(page, size));
    }
}
