package br.com.agendafacilsus.commonlibrary.domains.dtos;

import br.com.agendafacilsus.commonlibrary.domains.enums.UserRole;

import java.util.Collection;
import java.util.List;

import br.com.agendafacilsus.commonlibrary.domains.exceptions.ForbiddenException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static br.com.agendafacilsus.commonlibrary.domains.enums.UserRole.*;

public record UserDto(
    String id,
    String login,
    String password,
    UserRole role
){
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
