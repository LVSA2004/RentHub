package br.com.fiap.alugueis.aluguel;

import br.com.fiap.alugueis.Inquilino.Inquilino;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Data
@Entity
@Table(name = "TB_JJ_ALUGUEIS")
public class Aluguel {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_INQUILINO", referencedColumnName = "ID_INQUILINO",
            foreignKey = @ForeignKey(name = "FK_INQUILINO_ALUGUEL", value = ConstraintMode.CONSTRAINT))
    private Inquilino Inquilino;

    @NotBlank(message = "campo título é obrigatório")
    private String title;

    @Size(min = 10, message = "descrição deve ter pelo menos 10 caracteres")
    private String description;

    @Min(1000) @Max(5000)
    private Integer score;
}
