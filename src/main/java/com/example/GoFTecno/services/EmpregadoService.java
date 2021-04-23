package com.example.GoFTecno.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.example.GoFTecno.dtos.EmpregadoDTO;
import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.models.Empregado;
import com.example.GoFTecno.repositorys.EmpregadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpregadoService {

    @Autowired
    private EmpregadoRepository repository;

    public List<EmpregadoDTO> findAll() {
        return repository.findAll().stream().map(EmpregadoDTO::convert).collect(Collectors.toList());
    }

    public Page<EmpregadoDTO> findAll(Integer pagina, Integer tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return repository.findAll(pageable).map(EmpregadoDTO::convert);
    }

    public EmpregadoDTO findById(String cpf) {
        return repository.findById(cpf).map(EmpregadoDTO::convert)
                .orElseThrow(() -> new EntityNotFoundException("Empregado com cpf " + cpf + " não encontrado"));
    }

    public EmpregadoDTO findByEnome(String enome) {
        return repository.findByEnomeIgnoreCase(enome).map(EmpregadoDTO::convert)
                .orElseThrow(() -> new EntityNotFoundException("Empregado com nome " + enome + " não encontrado"));
    }

    public List<EmpregadoDTO> findByDepartamento(Departamento departamento) {
        return repository.findByDepartamento(departamento).stream().map(EmpregadoDTO::convert)
                .collect(Collectors.toList());
    }

    public List<EmpregadoDTO> findSupervisores() {
        return repository.findSupervisores().stream().map(EmpregadoDTO::convert).collect(Collectors.toList());
    }

    public List<EmpregadoDTO> findNotGerentes() {
        return repository.findNotGerentes().stream().map(EmpregadoDTO::convert).collect(Collectors.toList());
    }

    public EmpregadoDTO save(Empregado empregado) {
        return EmpregadoDTO.convert(repository.save(empregado));
    }

    public EmpregadoDTO update(String cpf, Empregado empregado) {
        if (repository.existsById(cpf)) {

            if (!cpf.equals(empregado.getCpf())) {
                throw new UnsupportedOperationException("CPF informado diferente do empregado.");
            }

            return EmpregadoDTO.convert(repository.save(empregado));
        } else
            throw new EntityNotFoundException("Empregado CPF: " + cpf);
    }

    public void delete(String cpf) {
        if (repository.existsById(cpf)) {
            repository.deleteById(cpf);
        } else
            throw new EntityNotFoundException("Empregado CPF: " + cpf);
    }
}
