package br.com.agendafacilsus.consultas.applicaton.usecase;

import br.com.agendafacilsus.consultas.exception.ConsultaNaoEncontradaException;
import br.com.agendafacilsus.consultas.infrastructure.controller.dto.ConsultaRequestDTO;
import br.com.agendafacilsus.consultas.infrastructure.controller.dto.ConsultaResponseDTO;
import br.com.agendafacilsus.consultas.infrastructure.gateway.ConsultaGateway;
import br.com.agendafacilsus.consultas.infrastructure.mapper.ConsultaMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.agendafacilsus.consultas.infrastructure.mapper.ConsultaMapper.toEntity;
import static br.com.agendafacilsus.consultas.infrastructure.mapper.ConsultaMapper.toResponseDTO;

@Service
@RequiredArgsConstructor
public class ConsultaUseCase {

    private final ConsultaGateway consultaGateway;

    public ConsultaResponseDTO criar(ConsultaRequestDTO dto) {
        val consulta = toEntity(dto);
        val salva = consultaGateway.salvar(consulta);
        return toResponseDTO(salva);
    }

    public List<ConsultaResponseDTO> listar() {
        return consultaGateway.listarConsultas()
                .stream()
                .map(ConsultaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ConsultaResponseDTO buscarPorId(Long id) {
        val consulta = consultaGateway.buscarPorId(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(id));
        return toResponseDTO(consulta);
    }

    public ConsultaResponseDTO atualizar(Long id, ConsultaRequestDTO dto) {
        consultaGateway.buscarPorId(id)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(id));

        val consulta = toEntity(dto);
        consulta.setId(id);
        val atualizada = consultaGateway.salvar(consulta);
        return toResponseDTO(atualizada);
    }

    public void excluir(Long id) {
        consultaGateway.excluir(id);
    }
}
