package com.example.api.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "EMPREGADOS")
public class Empregado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_NUMERO")
    private Long numero;

    @NotNull(message = "campo cpf não pode ser vazio")
    @Column(name = "EMP_CPF", nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "campo nome não pode ser vazio")
    @Column(name = "EMP_NOME", nullable = false)
    private String nome;

    @Column(name = "EMP_SALARIO", nullable = false)
    private Float salario;

    @ManyToOne
    @JoinColumn(name = "DEP_NUMERO")
    private Departamento departamento;

    public Empregado() { }

    public Empregado(Empregado empregado) {
        this.numero = empregado.numero;
        this.cpf = empregado.cpf;
        this.nome = empregado.nome;
        this.salario = empregado.salario;
        this.departamento = empregado.departamento;


    }
}
