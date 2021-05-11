package com.example.GoFTecno.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pnumero;

    @NotNull(message = "campo nome não pode ser vazio")
    @Column(unique = true)
    private String pnome;

    @ManyToOne
    @JoinColumn(name = "dnumero", referencedColumnName = "dnumero")
    private Departamento departamento;
}
