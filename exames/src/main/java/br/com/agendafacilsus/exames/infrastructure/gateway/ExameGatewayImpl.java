package br.com.agendafacilsus.exames.infrastructure.gateway;

import br.com.agendafacilsus.exames.domain.model.Exame;
import br.com.agendafacilsus.exames.exception.ExameGatewayException;
import br.com.agendafacilsus.exames.infrastructure.repository.ExameRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExameGatewayImpl implements ExameGateway {

    private static final Logger logger = LogManager.getLogger(ExameGatewayImpl.class);
    private final ExameRepository exameRepository;

    @Override
    public Exame salvar(Exame exame) {
        try {
            Exame exameSalvo = exameRepository.save(exame);
            logger.info("Exame salvo com sucesso: {}", exameSalvo::toString);
            return exameSalvo;
        } catch (Exception e) {
            logger.error("Erro ao salvar exame: {}", e.getMessage());
            throw new ExameGatewayException("Erro ao salvar exame", e);
        }
    }

    @Override
    public Optional<Exame> buscarPorId(Long id) {
        return exameRepository.findById(id);
    }

    @Override
    public List<Exame> listarExames() {
        return exameRepository.findAll();
    }

    @Override
    public void excluir(Long id) {
        try {
            exameRepository.deleteById(id);
            logger.info("Exame com ID {} excluído com sucesso.", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir exame: {}", e.getMessage());
            throw new ExameGatewayException("Erro ao excluir exame", e);
        }
    }
}
