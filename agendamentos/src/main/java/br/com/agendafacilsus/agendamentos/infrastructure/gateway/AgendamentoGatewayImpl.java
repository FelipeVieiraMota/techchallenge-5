package br.com.agendafacilsus.agendamentos.infrastructure.gateway;

import br.com.agendafacilsus.agendamentos.domain.model.Agendamento;
import br.com.agendafacilsus.agendamentos.exception.AgendamentoGatewayException;
import br.com.agendafacilsus.agendamentos.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AgendamentoGatewayImpl implements AgendamentoGateway {

    private static final Logger logger = LogManager.getLogger(AgendamentoGatewayImpl.class);

    private final AgendamentoRepository agendamentoRepository;

    @Override
    public Agendamento salvar(Agendamento agendamento) {
        try {
            Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
            logger.info("Agendamento salvo com sucesso: {}", agendamentoSalvo::toString);
            return agendamentoSalvo;
        } catch (Exception e) {
            logger.error("Erro ao salvar agendamento: {}", e.getMessage());
            throw new AgendamentoGatewayException("Erro ao salvar agendamento", e);
        }
    }

    @Override
    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    @Override
    public List<Agendamento> listarAgendamentos() {
        return agendamentoRepository.findAll();
    }

    @Override
    public void excluir(Long id) {
        try {
            agendamentoRepository.deleteById(id);
            logger.info("Agendamento com ID {} excluído com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir agendamento: {}", e.getMessage());
            throw new AgendamentoGatewayException("Erro ao excluir agendamento", e);
        }
    }
}
