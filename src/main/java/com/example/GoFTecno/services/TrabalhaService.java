package com.example.GoFTecno.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.example.GoFTecno.dtos.TrabalhaDTO;
import com.example.GoFTecno.models.Empregado;
import com.example.GoFTecno.models.Projeto;
import com.example.GoFTecno.models.Trabalha;
import com.example.GoFTecno.repositorys.TrabalhaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TrabalhaService {

    @Autowired
    private TrabalhaRepository repository;

    public List<TrabalhaDTO> findAll() {
        return repository.findAll().stream().map(TrabalhaDTO::convert).collect(Collectors.toList());
    }

    public Page<TrabalhaDTO> findAll(Integer pagina, Integer tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return repository.findAll(pageable).map(TrabalhaDTO::convert);
    }

    public TrabalhaDTO findById(Integer id) {
        return repository.findById(id).map(TrabalhaDTO::convert)
                .orElseThrow(() -> new EntityNotFoundException("Trabalho com id " + id + " não encontrado"));
    }

    public List<TrabalhaDTO> findByEmpregado(Empregado empregado) {
        return repository.findByEmpregado(empregado).stream().map(TrabalhaDTO::convert).collect(Collectors.toList());
    }

    public List<TrabalhaDTO> findByProjeto(Projeto projeto) {
        return repository.findByProjeto(projeto).stream().map(TrabalhaDTO::convert).collect(Collectors.toList());
    }

    public TrabalhaDTO save(Trabalha trabalha) {
        return TrabalhaDTO.convert(repository.save(trabalha));
    }

    public TrabalhaDTO update(Integer id, Trabalha trabalha) {
        if (repository.existsById(id)) {

            if (id != trabalha.getId()) {
                throw new UnsupportedOperationException("ID informado diferente do trabalho.");
            }

            return TrabalhaDTO.convert(repository.save(trabalha));
        } else
            throw new EntityNotFoundException("Trabalho id: " + id);
    }

    public void delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else
            throw new EntityNotFoundException("Trabalho id: " + id);
    }
}
