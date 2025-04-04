package br.com.agendafacilsus.autorizacaoeusuarios.mappers;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.FetchUserDto;

public interface IFetchMapper {
    FetchUserDto toDto(User entity);
}
