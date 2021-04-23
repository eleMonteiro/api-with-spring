package com.example.GoFTecno.dtos;

import com.example.GoFTecno.models.Empregado;

import lombok.Data;

@Data
public class SupervisorDTO {

    private String cpf;
    private String enome;

    public static SupervisorDTO convert(Empregado empregado) {
        SupervisorDTO supervisorDTO = new SupervisorDTO();

        supervisorDTO.cpf = empregado.getCpf();
        supervisorDTO.enome = empregado.getEnome();

        return supervisorDTO;
    }
}
