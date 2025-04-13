package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.UserDto;

public interface IUserMapper {
    UserDto toDto(User entity);
}
