package com.example.api.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "DEPARTAMENTOS")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEP_NUMERO")
    private Long numero;

    @NotEmpty(message = "campo nome não pode ser vazio")
    @Column(name = "DEP_NOME", nullable = false, unique = true)
    private String nome;

    public Departamento() {
    }

    public Departamento(Departamento departamento) {
        this.numero = departamento.numero;
        this.nome = departamento.nome;
    }
}
