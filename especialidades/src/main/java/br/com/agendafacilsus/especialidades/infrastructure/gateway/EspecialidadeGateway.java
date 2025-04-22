package br.com.agendafacilsus.especialidades.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;

import java.util.List;
import java.util.Optional;

public interface EspecialidadeGateway {
    Especialidade salvar(Especialidade consulta);
    Optional<Especialidade> buscarPorId(Long id);
    List<Especialidade> listar();
    void excluir(Long id);
}
