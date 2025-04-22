package br.com.agendafacilsus.commonlibrary.domain.model;

import br.com.agendafacilsus.commonlibrary.domain.enums.TipoEspecialidade;
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

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_especialidade", nullable = false)
    private TipoEspecialidade tipoEspecialidade;
}
