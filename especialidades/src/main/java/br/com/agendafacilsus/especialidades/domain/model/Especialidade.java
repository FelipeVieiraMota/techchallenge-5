package br.com.agendafacilsus.especialidades.domain.model;

import br.com.agendafacilsus.especialidades.domain.enums.TipoEspecialidade;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_especialidade", nullable = false)
    private TipoEspecialidade tipoEspecialidade;
}
