package com.example.GoFTecno.dtos;

import com.example.GoFTecno.models.Departamento;

import lombok.Data;

@Data
public class DepartamentoDTO {

    private Integer dnumero;
    private String dnome;
    private SupervisorDTO gerente;

    public static DepartamentoDTO convert(Departamento departamento) {
        DepartamentoDTO departamentoDTO = new DepartamentoDTO();

        departamentoDTO.dnumero = departamento.getDnumero();
        departamentoDTO.dnome = departamento.getDnome();

        if (departamento.getGerente() != null)
            departamentoDTO.gerente = SupervisorDTO.convert(departamento.getGerente());

        return departamentoDTO;
    }
}
