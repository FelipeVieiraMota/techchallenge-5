package br.com.agendafacilsus.exameseconsultas.applicaton.usecase;

import br.com.agendafacilsus.exameseconsultas.exception.ConsultaNaoEncontradaException;
import br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto.EspecialidadeRequestDTO;
import br.com.agendafacilsus.exameseconsultas.infrastructure.controller.dto.EspecialidadeResponseDTO;
import br.com.agendafacilsus.exameseconsultas.infrastructure.gateway.EspecialidadeGateway;
import br.com.agendafacilsus.exameseconsultas.infrastructure.mapper.EspecialidadeMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.agendafacilsus.exameseconsultas.infrastructure.mapper.EspecialidadeMapper.toEntity;
import static br.com.agendafacilsus.exameseconsultas.infrastructure.mapper.EspecialidadeMapper.toResponseDTO;

@Service
@RequiredArgsConstructor
public class EspecialidadeUseCase {

    private final EspecialidadeGateway consultaGateway;

    public EspecialidadeResponseDTO criar(EspecialidadeRequestDTO dto) {
        val consulta = toEntity(dto);
        val salva = consultaGateway.salvar(consulta);
        return toResponseDTO(salva);
    }

    public List<EspecialidadeResponseDTO> listar() {
        return consultaGateway.listarEspecialidades()
                .stream()
                .map(EspecialidadeMapper::toResponseDTO)
                .toList();
    }

    public EspecialidadeResponseDTO buscarPorId(Long id) {
        val consulta = consultaGateway.buscarPorId(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(id));
        return toResponseDTO(consulta);
    }

    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO dto) {
        if (consultaGateway.buscarPorId(id).isEmpty()) {
            throw new ConsultaNaoEncontradaException(id);
        }

        val consulta = toEntity(dto);
        consulta.setId(id);
        val atualizada = consultaGateway.salvar(consulta);
        return toResponseDTO(atualizada);
    }

    public void excluir(Long id) {
        consultaGateway.excluir(id);
    }
}
