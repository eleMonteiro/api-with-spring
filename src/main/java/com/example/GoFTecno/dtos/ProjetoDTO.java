package com.example.GoFTecno.dtos;

import com.example.GoFTecno.models.Projeto;

import lombok.Data;

@Data
public class ProjetoDTO {

    private Integer pnumero;
    private String pnome;
    private DepartamentoSGDTO departamento;

    public static ProjetoDTO convert(Projeto projeto) {
        ProjetoDTO projetoDTO = new ProjetoDTO();

        projetoDTO.pnumero = projeto.getPnumero();
        projetoDTO.pnome = projeto.getPnome();

        if (projeto.getDepartamento() != null)
            projetoDTO.departamento = DepartamentoSGDTO.convert(projeto.getDepartamento());

        return projetoDTO;
    }
}
