package br.com.agendafacilsus.commonlibrary.domains.dtos;

import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;
import br.com.agendafacilsus.commonlibrary.domains.exceptions.ForbiddenException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static br.com.agendafacilsus.commonlibrary.domains.enums.UserRole.*;

public record FetchUserDto(
        String id,
        String name,
        String login,
        UserRole role
) {
    public Collection<? extends GrantedAuthority> authorities() {

        if(this.role == ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_MEDICO"),
                    new SimpleGrantedAuthority("ROLE_PACIENTE")
            );
        }

        if(this.role == PACIENTE) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_PACIENTE")
            );
        }

        if(this.role == MEDICO) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_MEDICO")
            );
        }

        throw new ForbiddenException("Not Allowed.");
    }
}
