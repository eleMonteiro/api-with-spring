package com.example.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exportar {

    private Departamento departamento;
    private Empregado empregado;
    private Projeto projeto;
    private String formato;
    private String caminho;
    private String nomeArquivo;
}
