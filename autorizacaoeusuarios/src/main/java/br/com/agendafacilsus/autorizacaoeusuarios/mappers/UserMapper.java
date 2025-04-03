package br.com.agendafacilsus.autorizacaoeusuarios.mappers;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.UserDto;

public class UserMapper implements IUserMapper {

    @Override
    public UserDto toDto(User entity) {
        return new UserDto (
            entity.getId(),
            entity.getLogin(),
            entity.getPassword(),
            entity.getRole()
        );
    }
}
