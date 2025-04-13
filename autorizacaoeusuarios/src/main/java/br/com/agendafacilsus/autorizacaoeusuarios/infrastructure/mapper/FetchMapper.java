package br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.mapper;

import br.com.agendafacilsus.autorizacaoeusuarios.domain.model.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.FetchUserDto;

public class FetchMapper implements IFetchMapper{
    @Override
    public FetchUserDto toDto(User entity) {
        return new FetchUserDto (
                entity.getId(),
                entity.getName(),
                entity.getLogin(),
                entity.getRole()
        );
    }
}
