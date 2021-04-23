package com.example.GoFTecno.dtos;

import java.time.LocalDate;

import com.example.GoFTecno.models.Trabalha;

import lombok.Data;

@Data
public class TrabalhaDTO {

    private Integer id;
    private SupervisorDTO  empregado;
    private ProjetoTrablhoDTO projeto;
    private LocalDate inicio;
    private LocalDate termino;

    public static TrabalhaDTO convert(Trabalha trabalha) {
        TrabalhaDTO trabalhaDTO = new TrabalhaDTO();

        trabalhaDTO.id = trabalha.getId();

        if (trabalha.getEmpregado() != null)
            trabalhaDTO.empregado = SupervisorDTO.convert(trabalha.getEmpregado());

        if (trabalha.getProjeto() != null)
            trabalhaDTO.projeto = ProjetoTrablhoDTO.convert(trabalha.getProjeto());

        trabalhaDTO.inicio = trabalha.getInicio();
        trabalhaDTO.termino = trabalha.getTermino();

        return trabalhaDTO;
    }
}
