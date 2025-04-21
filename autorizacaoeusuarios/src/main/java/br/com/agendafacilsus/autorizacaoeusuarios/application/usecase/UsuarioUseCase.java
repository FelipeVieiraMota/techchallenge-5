package br.com.agendafacilsus.autorizacaoeusuarios.application.usecase;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioRequestDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.domain.dto.UsuarioResponseDTO;
import br.com.agendafacilsus.autorizacaoeusuarios.exception.UsuarioNaoEncontradoException;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.gateway.UsuarioGateway;
import br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.UsuarioMapper;
import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.UsuarioMapper.toEntity;
import static br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper.UsuarioMapper.toResponseDTO;

@Service
@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        val senhaCriptografada = new BCryptPasswordEncoder().encode(dto.senha());
        val usuario = Usuario.builder()
                .nome(dto.nome())
                .login(dto.login())
                .senha(senhaCriptografada)
                .role(dto.role())
                .build();
        val usuarioSalvo = usuarioGateway.salvar(usuario);
        return toResponseDTO(usuarioSalvo);
    }

    public List<UsuarioResponseDTO> listar() {
        return usuarioGateway.listar()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(String id) {
        val usuario = usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        return toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizar(String id, UsuarioRequestDTO dto) {
        if (usuarioGateway.buscarPorId(id).isEmpty()) {
            throw new UsuarioNaoEncontradoException(id);
        }

        val usuario = toEntity(dto);
        usuario.setId(id);
        return toResponseDTO(usuarioGateway.salvar(usuario));
    }

    public void excluir(String id) {
        if (usuarioGateway.buscarPorId(id).isEmpty()) {
            throw new UsuarioNaoEncontradoException(id);
        }

        usuarioGateway.excluir(id);
    }

    public UserDetails buscarPorLogin(String token) {
        return usuarioGateway.buscarPorLogin(token);
    }

    public Page<UsuarioResponseDTO> buscarPorPapel(UserRole role, int page, int size) {
        return usuarioGateway.buscarPorPapel(role, page, size)
                .map(UsuarioMapper::toResponseDTO);
    }
}
