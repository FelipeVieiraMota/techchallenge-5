package br.com.agendafacilsus.exames.infrastructure.gateway;

import br.com.agendafacilsus.exames.domain.model.Exame;

import java.util.List;
import java.util.Optional;

public interface ExameGateway {
    Exame salvar(Exame exame);
    Optional<Exame> buscarPorId(Long id);
    List<Exame> listarExames();
    void excluir(Long id);
}
