package br.com.agendafacilsus.agendamentos.domain.model;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_agendamento")
@Entity(name = "tb_agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;
//    private Long medicoId;
    private String nomePaciente;
    private Long referenciaId; // ID da consulta ou exame relacionado
    private LocalDateTime dataHora;
    private StatusAgendamento status;
}
