package br.com.agendafacilsus.autorizacaoeusuarios.mappers;

import br.com.agendafacilsus.autorizacaoeusuarios.domains.entity.User;
import br.com.agendafacilsus.commonlibrary.domains.dtos.FetchUserDto;

public class FetchMapper implements IFetchMapper{
    @Override
    public FetchUserDto toDto(User entity) {
        return new FetchUserDto (
                entity.getId(),
                entity.getLogin(),
                entity.getRole()
        );
    }
}
