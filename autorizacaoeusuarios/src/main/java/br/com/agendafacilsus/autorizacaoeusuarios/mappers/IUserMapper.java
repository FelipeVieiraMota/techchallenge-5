package br.com.agendafacilsus.autorizacaoeusuarios.mappers;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.UserDto;

public interface IUserMapper {
    UserDto toDto(User entity);
}
