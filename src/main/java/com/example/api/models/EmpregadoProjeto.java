package com.example.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "EMPREGADO_PROJETO")
public class EmpregadoProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPRO_ID")
    private Long numero;

    @ManyToOne
    @JoinColumn(name = "EMP_NUMERO")
    private Empregado empregado;

    @ManyToOne
    @JoinColumn(name = "PRO_NUMERO")
    private Projeto projeto;

    @NotNull(message = "campo data de inicio não pode ser vazio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "EMPRO_INICIO")
    private LocalDate inicio;

    @NotNull(message = "campo data de termino não pode ser vazio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "EMPRO_TERMINO")
    private LocalDate termino;

    public EmpregadoProjeto() {
    }

    public EmpregadoProjeto(EmpregadoProjeto empregadoProjeto) {
        this.numero = empregadoProjeto.numero;
        this.empregado = empregadoProjeto.empregado;
        this.projeto = empregadoProjeto.projeto;
        this.inicio = empregadoProjeto.inicio;
        this.termino = empregadoProjeto.termino;
    }
}
