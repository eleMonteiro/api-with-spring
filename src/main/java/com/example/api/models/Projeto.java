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
@Table(name = "PROJETOS")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRO_NUMERO")
    private Long numero;

    @NotNull(message = "campo nome não pode ser vazio")
    @Column(name = "PRO_NOME", nullable = false, unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "DEP_NUMERO")
    private Departamento departamento;

    public Projeto() {
    }

    public Projeto(Projeto projeto) {
        this.numero = projeto.numero;
        this.nome = projeto.nome;
        this.departamento = projeto.departamento;
    }


}
