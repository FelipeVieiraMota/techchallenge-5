package br.com.agendafacilsus.agendamentos.domain.model;

import br.com.agendafacilsus.agendamentos.domain.enums.StatusAgendamento;
import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @Column(nullable = false)
    private String uuidPaciente;

    @Column(nullable = false)
    private String nomePaciente;

    @Column(nullable = false)
    private String uuidMedico;

    @Column(nullable = false)
    private String nomeMedico;

    @Column(nullable = false)
    private String descricaoEspecialidade;

    @Column(nullable = false)
    private TipoEspecialidade tipoEspecialidade;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;
}

