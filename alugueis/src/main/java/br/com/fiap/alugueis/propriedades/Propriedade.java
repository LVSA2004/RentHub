package br.com.fiap.alugueis.propriedades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_JJ_PROPRIEDADES")
public class Propriedade {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;

    @NotBlank(message = "O Campo tipo é obrigatório")
    private String tipo;

    @Size(min = 50, message = "descrição deve ter pelo menos 50 caracteres")
    private String description;

    @NotBlank(message = "O Campo Endereço é obrigatório")
    private String endereco;

    private String endereco2;

    @NotBlank(message = "O Campo Cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "O Campo CEP é obrigatório")
    private String cep;

    @NotBlank(message = "O Campo Estado é obrigatório")
    private String estado;

    @NotBlank(message = "O Campo País é obrigatório")
    private String pais;

    @NotBlank(message = "O Campo Metragem é obrigatório")
    private String metragem;

    @NotBlank(message = "O Campo Número de Comodos é obrigatório")
    private String comodos;

    @NotBlank(message = "O Campo Número de Quartos é obrigatório")
    private String quartos;

    @NotBlank(message = "O Campo Metragem é obrigatório")
    private String banheiros;

}
