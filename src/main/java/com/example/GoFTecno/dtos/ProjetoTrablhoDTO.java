package com.example.GoFTecno.dtos;

import com.example.GoFTecno.models.Projeto;

import lombok.Data;

@Data
public class ProjetoTrablhoDTO {
    
    private Integer pnumero;
    private String pnome;

    public static ProjetoTrablhoDTO convert(Projeto projeto) {
        ProjetoTrablhoDTO projetoTrablhoDTO = new ProjetoTrablhoDTO();

        projetoTrablhoDTO.pnumero = projeto.getPnumero();
        projetoTrablhoDTO.pnome = projeto.getPnome();

        return projetoTrablhoDTO;
    }
}
