package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.UserDto;

public class UserMapper implements IUserMapper {

    @Override
    public UserDto toDto(User entity) {
        return new UserDto (
            entity.getId(),
            entity.getName(),
            entity.getLogin(),
            entity.getPassword(),
            entity.getRole()
        );
    }
}
