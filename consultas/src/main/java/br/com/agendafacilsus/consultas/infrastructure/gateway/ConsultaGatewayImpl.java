package br.com.agendafacilsus.consultas.infrastructure.gateway;

import br.com.agendafacilsus.consultas.domain.model.Consulta;
import br.com.agendafacilsus.consultas.exception.ConsultaGatewayException;
import br.com.agendafacilsus.consultas.infrastructure.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConsultaGatewayImpl implements ConsultaGateway {

    private static final Logger logger = LogManager.getLogger(ConsultaGatewayImpl.class);

    private final ConsultaRepository consultaRepository;

    @Override
    public Consulta salvar(Consulta consulta) {
        try {
            Consulta consultaSalva = consultaRepository.save(consulta);
            logger.info("Consulta salva com sucesso: {}", consultaSalva::toString);
            return consultaSalva;
        } catch (Exception e) {
            logger.error("Erro ao salvar consulta: {}", e.getMessage());
            throw new ConsultaGatewayException("Erro ao salvar consulta", e);
        }
    }

    @Override
    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    @Override
    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    @Override
    public void excluir(Long id) {
        try {
            consultaRepository.deleteById(id);
            logger.info("Consulta com ID {} excluída com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir consulta: {}", e.getMessage());
            throw new ConsultaGatewayException("Erro ao excluir consulta", e);
        }
    }
}
