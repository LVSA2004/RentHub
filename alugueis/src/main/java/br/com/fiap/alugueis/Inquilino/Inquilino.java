package br.com.fiap.alugueis.Inquilino;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_JJ_INQUILINOS")
public class Inquilino {

    @Column(name = "ID_INQUILINO")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O Campo Nome é obrigatório")
    private String nome;

    @NotBlank(message = "O Campo Sobrenome é obrigatório")
    private String sobrenome;

    @NotBlank(message = "O Campo CPF é obrigatório")
    private int cpf;

    @NotBlank(message = "O Campo E-mail é obrigatório")
    private String email;

    @NotBlank(message = "O Campo Telefone é obrigatório")
    private String telefone;
}
