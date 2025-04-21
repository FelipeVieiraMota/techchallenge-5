package br.com.agendafacilsus.especialidades.applicaton.usecase;

import br.com.agendafacilsus.commonlibrary.domain.exception.EspecialidadeNaoEncontradaException;
import br.com.agendafacilsus.especialidades.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.commonlibrary.domain.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.especialidades.infrastructure.gateway.EspecialidadeGateway;
import br.com.agendafacilsus.especialidades.infrastructure.mapper.EspecialidadeMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.agendafacilsus.especialidades.infrastructure.mapper.EspecialidadeMapper.toEntity;
import static br.com.agendafacilsus.especialidades.infrastructure.mapper.EspecialidadeMapper.toResponseDTO;

@Service
@RequiredArgsConstructor
public class EspecialidadeUseCase {

    private final EspecialidadeGateway consultaGateway;

    public EspecialidadeResponseDTO criar(EspecialidadeRequestDTO dto) {
        val especialidade = toEntity(dto);
        return toResponseDTO(consultaGateway.salvar(especialidade));
    }

    public List<EspecialidadeResponseDTO> listar() {
        return consultaGateway.listar()
                .stream()
                .map(EspecialidadeMapper::toResponseDTO)
                .toList();
    }

    public EspecialidadeResponseDTO buscarPorId(Long id) {
        val especialidade = consultaGateway.buscarPorId(id)
                .orElseThrow(() -> new EspecialidadeNaoEncontradaException(id));
        return toResponseDTO(especialidade);
    }

    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO dto) {
        if (consultaGateway.buscarPorId(id).isEmpty()) {
            throw new EspecialidadeNaoEncontradaException(id);
        }

        val especialidade = toEntity(dto);
        especialidade.setId(id);
        return toResponseDTO(consultaGateway.salvar(especialidade));
    }

    public void excluir(Long id) {
        if (consultaGateway.buscarPorId(id).isEmpty()) {
            throw new EspecialidadeNaoEncontradaException(id);
        }

        consultaGateway.excluir(id);
    }
}
