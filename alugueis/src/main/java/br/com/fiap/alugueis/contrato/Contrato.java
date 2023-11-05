package br.com.fiap.alugueis.contrato;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Data
@Entity
@Getter
@Setter
@Table(name = "TB_CONTRATOS")
public class Contrato {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo título é obrigatório")
    private String title;

    @Min(1) @Max(10)
    private Integer score;

}
