package com.example.GoFTecno.dtos;

import com.example.GoFTecno.models.Departamento;

import lombok.Data;

@Data
public class DepartamentoSGDTO {
    
    private Integer dnumero;
    private String dnome;

    public static DepartamentoSGDTO convert(Departamento departamento) {
        DepartamentoSGDTO departamentoDTO = new DepartamentoSGDTO();

        departamentoDTO.dnumero = departamento.getDnumero();
        departamentoDTO.dnome = departamento.getDnome();

        
        return departamentoDTO;
    }
}
