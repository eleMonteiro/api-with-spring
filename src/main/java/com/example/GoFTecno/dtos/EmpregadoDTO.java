package com.example.GoFTecno.dtos;

import com.example.GoFTecno.models.Empregado;

import lombok.Data;

@Data
public class EmpregadoDTO {

    private String cpf;
    private String enome;
    private Float salario;
    private SupervisorDTO supervisor;
    private DepartamentoSGDTO departamento;

    public static EmpregadoDTO convert(Empregado empregado) {
        EmpregadoDTO empregadoDTO = new EmpregadoDTO();

        empregadoDTO.cpf = empregado.getCpf();
        empregadoDTO.enome = empregado.getEnome();
        empregadoDTO.salario = empregado.getSalario();

        if (empregado.getSupervisor() != null)
            empregadoDTO.supervisor = SupervisorDTO.convert(empregado.getSupervisor());

        if (empregado.getDepartamento() != null)
            empregadoDTO.departamento = DepartamentoSGDTO.convert(empregado.getDepartamento());

        return empregadoDTO;
    }

}
