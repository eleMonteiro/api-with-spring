package com.example.GoFTecno.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dnumero;

    @NotNull(message = "campo nome não pode ser vazio")
    @Column(unique = true)
    private String dnome;

    @OneToOne
    @JoinColumn(name = "cpf_gerente", referencedColumnName = "cpf")
    private Empregado gerente;
}
