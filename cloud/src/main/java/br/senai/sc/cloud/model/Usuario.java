package br.senai.sc.cloud.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Table(name = "tb_usuario") // Define um nome para a tabela de usuario como "tb_usuario"
//@Column(name = "cpf_usuario") // Define um nome para a coluna de cpf usuario como "cpf_usuario"


@ToString
@Getter
@Entity
public class Usuario {

    @Id // indica a primary key da entidade
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gera um valor aleatório para o id, o banco de dados que gerará o id aleatório
    private Integer id;

    @Column(precision = 11,updatable = false, unique = true)
    private Long cpf;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String email;

    @ToString.Exclude
    @Setter
    @JsonIgnore
    @Column(name = "password")
    private String senha;

    @Column(length = 50)
    private String nomePet;

}
