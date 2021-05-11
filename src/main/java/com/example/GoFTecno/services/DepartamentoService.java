package com.example.GoFTecno.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.example.GoFTecno.dtos.DepartamentoDTO;
import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.repositorys.DepartamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository repository;

    public List<DepartamentoDTO> findAll() {
        return repository.findAll().stream().map(DepartamentoDTO::convert).collect(Collectors.toList());
    }

    public Page<DepartamentoDTO> findAll(Integer pagina, Integer tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return repository.findAll(pageable).map(DepartamentoDTO::convert);
    }

    public DepartamentoDTO findById(Integer dnumero) {
        return repository.findById(dnumero).map(DepartamentoDTO::convert)
                .orElseThrow(() -> new EntityNotFoundException("Departamento com id " + dnumero + " não encontrado"));
    }

    public DepartamentoDTO findByDnome(String dnome) {
        return repository.findByDnomeIgnoreCase(dnome).map(DepartamentoDTO::convert)
                .orElseThrow(() -> new EntityNotFoundException("Departamento com nome " + dnome + " não encontrado"));
    }

    public List<DepartamentoDTO> findByGerenteIsNull() {
        return repository.findByGerenteIsNull().stream().map(DepartamentoDTO::convert).collect(Collectors.toList());
    }

    public DepartamentoDTO save(Departamento departamento) {
        repository.findByDnomeIgnoreCase(departamento.getDnome()).ifPresent(d -> {
            throw new EntityExistsException("Departamento com nome " + d.getDnome() + " já existe");
        });

        return DepartamentoDTO.convert(repository.save(departamento));
    }

    public DepartamentoDTO update(Integer dnumero, Departamento departamento) {
        repository.findById(dnumero).orElseThrow(() -> new EntityNotFoundException("Departamento com id " + dnumero + " não encontrado"));

        if (dnumero != departamento.getDnumero())
            throw new UnsupportedOperationException("ID informado diferente do Departamento.");

        repository.findByDnomeIgnoreCase(departamento.getDnome()).ifPresent(d -> {
            if (d.getDnumero() != departamento.getDnumero())
                throw new EntityExistsException("Departamento com nome " + d.getDnome() + " já existe");
        });

        return DepartamentoDTO.convert(repository.save(departamento));
    }

    public void delete(Integer dnumero) {
        repository.findById(dnumero)
                .orElseThrow(() -> new EntityNotFoundException("Departamento com id " + dnumero + " não encontrado"));
        repository.deleteById(dnumero);
    }
}
