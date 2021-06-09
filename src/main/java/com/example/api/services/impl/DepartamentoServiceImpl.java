package com.example.api.services.impl;

import com.example.api.models.Departamento;
import com.example.api.repositorys.DepartamentoRepository;
import com.example.api.services.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository repository;

    @Override
    public Departamento save(Departamento departamento) {
        repository.findByNomeIgnoreCase(departamento.getNome()).ifPresent(d -> {
            throw new EntityExistsException("Departamento com nome " + d.getNome() + " já existe");
        });

        return repository.save(departamento);
    }

    @Override
    public Departamento update(Long numero, Departamento departamento) {
        Departamento dept = repository.findById(numero).orElseThrow(() ->
                new EntityNotFoundException("Departamento com identificador " + numero + " não encontrado"));

        if (!Objects.equals(numero, departamento.getNumero()))
            throw new UnsupportedOperationException("ID informado diferente do ID do Departamento.");

        repository.findByNomeIgnoreCase(departamento.getNome()).ifPresent(d -> {
            if (!Objects.equals(d.getNumero(), departamento.getNumero()))
                throw new EntityExistsException("Departamento com nome " + d.getNome() + " já existe");
        });

        departamento.setNumero(dept.getNumero());
        return repository.saveAndFlush(departamento);
    }

    @Override
    public void delete(Long numero) {
        repository.findById(numero)
                .orElseThrow(() -> new EntityNotFoundException("Departamento com id " + numero + " não encontrado"));

        repository.deleteById(numero);
    }

    @Override
    public Page<Departamento> findFilter(Departamento filtro, Pageable pageable) {
        Example<Departamento> example = Example.of(new Departamento());

        return repository.findAll(getSpecFromDatesAndExample(filtro, example), pageable)
                .map(Departamento::new);
    }

    private Specification<Departamento> getSpecFromDatesAndExample(Departamento filtro, Example<Departamento> example) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            Optional<Departamento> optional = Optional.ofNullable(filtro);
            Departamento departamento = optional.orElse(new Departamento());

            if (Objects.nonNull(departamento.getNumero()))
                predicates.add(builder.equal(root.get("numero"), departamento.getNumero()));

            if (Objects.nonNull(departamento.getNome()))
                predicates.add(builder.like(builder.lower(root.get("nome")), "%" + departamento.getNome() + "%"));

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
