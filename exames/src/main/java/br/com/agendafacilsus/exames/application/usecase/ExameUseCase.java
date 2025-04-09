package br.com.agendafacilsus.exames.application.usecase;

import br.com.agendafacilsus.exames.exception.ExameNaoEncontradoException;
import br.com.agendafacilsus.exames.infrastructure.controller.dto.ExameRequestDTO;
import br.com.agendafacilsus.exames.infrastructure.controller.dto.ExameResponseDTO;
import br.com.agendafacilsus.exames.infrastructure.gateway.ExameGateway;
import br.com.agendafacilsus.exames.infrastructure.mapper.ExameMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.agendafacilsus.exames.infrastructure.mapper.ExameMapper.*;

@Service
@RequiredArgsConstructor
public class ExameUseCase {

    private final ExameGateway exameGateway;

    public ExameResponseDTO criar(ExameRequestDTO dto) {
        val exame = toEntity(dto);
        val salvo = exameGateway.salvar(exame);
        return toResponseDTO(salvo);
    }

    public List<ExameResponseDTO> listar() {
        return exameGateway.listarExames().stream()
                .map(ExameMapper::toResponseDTO)
                .toList();
    }

    public ExameResponseDTO buscarPorId(Long id) {
        return exameGateway.buscarPorId(id)
                .map(ExameMapper::toResponseDTO)
                .orElseThrow(() -> new ExameNaoEncontradoException(id));
    }

    public ExameResponseDTO atualizar(Long id, ExameRequestDTO dto) {
        exameGateway.buscarPorId(id)
                .orElseThrow(() -> new ExameNaoEncontradoException(id));

        val exame = toEntity(dto);
        exame.setId(id);
        val atualizado = exameGateway.salvar(exame);
        return toResponseDTO(atualizado);
    }

    public void excluir(Long id) {
        exameGateway.excluir(id);
    }
}
