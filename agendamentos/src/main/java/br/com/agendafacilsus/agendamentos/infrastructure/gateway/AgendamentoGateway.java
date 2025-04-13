package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;

import java.util.List;
import java.util.Optional;

public interface AgendamentoGateway {
    Agendamento salvar(Agendamento agendamento);
    Optional<Agendamento> buscarPorId(Long id);
    List<Agendamento> listar();
    void excluir(Long id);
}