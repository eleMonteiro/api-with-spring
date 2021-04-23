package com.example.GoFTecno.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class Trabalha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    private Empregado empregado;

    @ManyToOne
    @JoinColumn(name = "pnumero", referencedColumnName = "pnumero")
    private Projeto projeto;

    @NotNull(message = "campo data de inicio não pode ser vazio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;

    @NotNull(message = "campo data de termino não pode ser vazio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate termino;
}
