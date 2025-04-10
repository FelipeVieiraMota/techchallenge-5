package br.com.agendafacilsus.exameseconsultas.infrastructure.gateway;

import br.com.agendafacilsus.exameseconsultas.domain.model.Especialidade;

import java.util.List;
import java.util.Optional;

public interface EspecialidadeGateway {
    Especialidade salvar(Especialidade consulta);
    Optional<Especialidade> buscarPorId(Long id);
    List<Especialidade> listarEspecialidades();
    void excluir(Long id);
}
