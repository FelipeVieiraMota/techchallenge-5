package br.com.agendafacilsus.consultas.infrastructure.gateway;

import br.com.agendafacilsus.consultas.domain.model.Consulta;

import java.util.List;
import java.util.Optional;

public interface ConsultaGateway {
    Consulta salvar(Consulta consulta);
    Optional<Consulta> buscarPorId(Long id);
    List<Consulta> listarConsultas();
    void excluir(Long id);
}
