package com.example.GoFTecno.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.example.GoFTecno.dtos.ProjetoDTO;
import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.models.Projeto;
import com.example.GoFTecno.repositorys.ProjetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository repository;

    public List<ProjetoDTO> findAll() {
        return repository.findAll().stream().map(ProjetoDTO::convert).collect(Collectors.toList());
    }

    public Page<ProjetoDTO> findAll(Integer pagina, Integer tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return repository.findAll(pageable).map(ProjetoDTO::convert);
    }

    public ProjetoDTO findById(Integer pnumero) {
        return repository.findById(pnumero).map(ProjetoDTO::convert)
                .orElseThrow(() -> new EntityNotFoundException("Projeto com id " + pnumero + " não encontrado"));
    }

    public List<ProjetoDTO> findByDepartamento(Departamento departamento) {
        return repository.findByDepartamento(departamento).stream().map(ProjetoDTO::convert)
                .collect(Collectors.toList());
    }

    public ProjetoDTO save(Projeto projeto) {
        return ProjetoDTO.convert(repository.save(projeto));
    }

    public ProjetoDTO update(Integer pnumero, Projeto projeto) {
        if (repository.existsById(pnumero)) {

            if (pnumero != projeto.getPnumero()) {
                throw new UnsupportedOperationException("ID informado diferente do projeto.");
            }

            return ProjetoDTO.convert(repository.save(projeto));
        } else
            throw new EntityNotFoundException("Projeto id: " + pnumero);
    }

    public void delete(Integer pnumero) {
        if (repository.existsById(pnumero)) {
            repository.deleteById(pnumero);
        } else
            throw new EntityNotFoundException("Projeto id: " + pnumero);
    }

}
