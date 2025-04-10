package br.com.agendafacilsus.exameseconsultas.infrastructure.gateway;

import br.com.agendafacilsus.exameseconsultas.domain.model.Especialidade;
import br.com.agendafacilsus.exameseconsultas.exception.ConsultaGatewayException;
import br.com.agendafacilsus.exameseconsultas.infrastructure.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EspecialidadeGatewayImpl implements EspecialidadeGateway {

    private static final Logger logger = LogManager.getLogger(EspecialidadeGatewayImpl.class);

    private final EspecialidadeRepository consultaRepository;

    @Override
    public Especialidade salvar(Especialidade consulta) {
        try {
            Especialidade consultaSalva = consultaRepository.save(consulta);
            logger.info("Especialidade salva com sucesso: {}", consultaSalva::toString);
            return consultaSalva;
        } catch (Exception e) {
            logger.error("Erro ao salvar especialidade: {}", e.getMessage());
            throw new ConsultaGatewayException("Erro ao salvar especialidade", e);
        }
    }

    @Override
    public List<Especialidade> listarEspecialidades() {
        return consultaRepository.findAll();
    }

    @Override
    public Optional<Especialidade> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    @Override
    public void excluir(Long id) {
        try {
            consultaRepository.deleteById(id);
            logger.info("Especialidade com ID {} excluída com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir especialidade: {}", e.getMessage());
            throw new ConsultaGatewayException("Erro ao excluir especialidade", e);
        }
    }
}
