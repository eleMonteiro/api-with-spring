package com.example.GoFTecno.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "empregados")
public class Empregado {

    @Id
    private String cpf;

    @NotNull(message = "campo nome não pode ser vazio")
    private String enome;

    private Float salario;

    @OneToOne
    @JoinColumn(name = "cpf_supervisor", referencedColumnName = "cpf")
    private Empregado supervisor;

    @ManyToOne
    @JoinColumn(name = "dnumero", referencedColumnName = "dnumero")
    private Departamento departamento;
}
