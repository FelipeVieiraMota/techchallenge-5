package br.com.agendafacilsus.exameseconsultas.domain.model;

import br.com.agendafacilsus.exameseconsultas.domain.enums.TipoEspecialidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_especialidade")
@Entity(name = "tb_especialidade")
public class Especialidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private TipoEspecialidade tipoEspecialidade;
}
