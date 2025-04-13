package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.FetchUserDto;

public interface IFetchMapper {
    FetchUserDto toDto(User entity);
}
