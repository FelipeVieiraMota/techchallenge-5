package br.com.agendafacilsus.consultas.domain.model;

import br.com.agendafacilsus.consultas.domain.enums.EspecialidadeConsulta;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_consulta")
@Entity(name = "tb_consulta")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EspecialidadeConsulta especialidade;
}
