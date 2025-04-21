package br.com.agendafacilsus.agendamentos.domain.model;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.commonlibrary.domain.model.Especialidade;
import br.com.agendafacilsus.commonlibrary.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tb_agendamento")
@Table(name = "tb_agendamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id", nullable = false)
    private Usuario paciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_medico", referencedColumnName = "id", nullable = false)
    private Usuario medico;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_especialidade", referencedColumnName = "id", nullable = false)
    private Especialidade especialidade;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;
}

