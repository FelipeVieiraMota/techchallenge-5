package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.repository;

import br.com.agendafacilsus.commonlibrary.domain.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveVerificarExistenciaDeLoginIgnoreCase() {
        Usuario usuario = Usuario.builder()
                .nome("João")
                .login("admin")
                .senha("123")
                .role(UserRole.ADMIN)
                .build();

        usuarioRepository.save(usuario);

        assertTrue(usuarioRepository.existsByLoginIgnoreCase("ADMIN"));
        assertTrue(usuarioRepository.existsByLoginIgnoreCase("admin"));
        assertFalse(usuarioRepository.existsByLoginIgnoreCase("outro"));
    }

    @Test
    void deveRetornarUserDetailsPorLogin() {
        Usuario usuario = Usuario.builder()
                .nome("Maria")
                .login("maria")
                .senha("senha123")
                .role(UserRole.PACIENTE)
                .build();

        usuarioRepository.save(usuario);

        UserDetails userDetails = usuarioRepository.findByLogin("maria");

        assertNotNull(userDetails);
        assertEquals("maria", userDetails.getUsername());
    }

    @Test
    void deveBuscarUsuariosPorRoleComPaginacao() {
        usuarioRepository.save(Usuario.builder().nome("User1").login("u1").senha("123").role(UserRole.MEDICO).build());
        usuarioRepository.save(Usuario.builder().nome("User2").login("u2").senha("123").role(UserRole.MEDICO).build());
        usuarioRepository.save(Usuario.builder().nome("User3").login("u3").senha("123").role(UserRole.ADMIN).build());

        var page = usuarioRepository.findByRole(UserRole.MEDICO, PageRequest.of(0, 10));

        assertEquals(2, page.getTotalElements());
        assertTrue(page.getContent().stream().allMatch(u -> u.getRole() == UserRole.MEDICO));
    }
}
