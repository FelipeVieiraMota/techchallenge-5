package br.com.agendafacilsus.especialidades.infrastructure.gateway;

import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.especialidades.exception.EspecialidadeDuplicadaException;
import br.com.agendafacilsus.especialidades.exception.EspecialidadeGatewayException;
import br.com.agendafacilsus.especialidades.infrastructure.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EspecialidadeGatewayImpl implements EspecialidadeGateway {

    private static final Logger logger = LogManager.getLogger(EspecialidadeGatewayImpl.class);

    private final EspecialidadeRepository especialidadeRepository;

    @Override
    public Especialidade salvar(Especialidade especialidade) {
        try {
            val jaExisteEspecialidade = especialidadeRepository.existsByDescricaoIgnoreCase(especialidade.getDescricao());

            if (jaExisteEspecialidade) {
                logger.warn("Tentativa de salvar especialidade duplicada: {}", especialidade.getDescricao());
                throw new EspecialidadeDuplicadaException();
            }

            val consultaSalva = especialidadeRepository.save(especialidade);
            logger.info("Especialidade salva com sucesso: {}", consultaSalva::toString);
            return consultaSalva;

        } catch (Exception e) {
            logger.error("Erro ao salvar especialidade: {}", e.getMessage());
            throw new EspecialidadeGatewayException("Erro ao salvar especialidade", e);
        }
    }

    @Override
    public List<Especialidade> listar() {
        return especialidadeRepository.findAll();
    }

    @Override
    public Optional<Especialidade> buscarPorId(Long id) {
        return especialidadeRepository.findById(id);
    }

    @Override
    public void excluir(Long id) {
        try {
            especialidadeRepository.deleteById(id);
            logger.info("Especialidade com ID {} excluída com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir especialidade: {}", e.getMessage());
            throw new EspecialidadeGatewayException("Erro ao excluir especialidade", e);
        }
    }
}
