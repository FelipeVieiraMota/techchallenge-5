package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.user;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioJPGatewayException;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.repository.UsuarioJPRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioJPGatewayImpl implements UsuarioJPGateway {

    private static final Logger logger = LogManager.getLogger(UsuarioJPGatewayImpl.class);

    private final UsuarioJPRepository usuarioJPRepository;

    @Override
    public User salvar(User usuario) {
        try {
            val agendamentoSalvo = usuarioJPRepository.save(usuario);
            logger.info("Usuário salvo com sucesso: {}", agendamentoSalvo::toString);
            return agendamentoSalvo;
        } catch (Exception e) {
            logger.error("Erro ao salvar usuário: {}", e.getMessage());
            throw new UsuarioJPGatewayException("Erro ao salvar usuário", e);
        }
    }

    @Override
    public Optional<User> buscarPorId(String id) {
        return usuarioJPRepository.findById(id);
    }

    @Override
    public List<User> listar() {
        return usuarioJPRepository.findAll();
    }

    @Override
    public void excluir(String id) {
        try {
            usuarioJPRepository.deleteById(id);
            logger.info("Usuário com ID {} excluído com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir usuário: {}", e.getMessage());
            throw new UsuarioJPGatewayException("Erro ao excluir usuário", e);
        }
    }
}
