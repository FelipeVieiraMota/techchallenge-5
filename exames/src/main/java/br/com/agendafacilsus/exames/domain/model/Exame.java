package br.com.agendafacilsus.exames.domain.model;

import br.com.agendafacilsus.exames.domain.enums.TipoExame;
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
@Table(name = "tb_exame")
@Entity(name = "tb_exame")
public class Exame {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private TipoExame tipoExame;
}
