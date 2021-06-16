package com.example.api.services.impl;

import com.example.api.models.EmpregadoProjeto;
import com.example.api.repositorys.EmpregadoProjetoRepository;
import com.example.api.repositorys.EmpregadoRepository;
import com.example.api.repositorys.ProjetoRepository;
import com.example.api.services.EmpregadoProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmpregadoProjetoServiceImpl implements EmpregadoProjetoService {

    private final EmpregadoProjetoRepository repository;

    private final EmpregadoRepository empregadoRepository;

    private final ProjetoRepository projetoRepository;

    @Autowired
    public EmpregadoProjetoServiceImpl(EmpregadoProjetoRepository repository, EmpregadoRepository empregadoRepository, ProjetoRepository projetoRepository) {
        this.repository = repository;
        this.empregadoRepository = empregadoRepository;
        this.projetoRepository = projetoRepository;
    }

    @Override
    public EmpregadoProjeto save(EmpregadoProjeto empregadoProjeto) {

        validate(empregadoProjeto);

        return repository.save(empregadoProjeto);
    }

    @Override
    public EmpregadoProjeto update(Long numero, EmpregadoProjeto empregadoProjeto) {

        EmpregadoProjeto empro = repository.findById(numero).orElseThrow(() ->
                new EntityNotFoundException("Não encontrado empregado relacionado ao projeto"));

        validate(empregadoProjeto);

        empregadoProjeto.setNumero(empro.getNumero());
        return repository.saveAndFlush(empregadoProjeto);
    }

    @Override
    public void delete(Long numero) {
        repository.findById(numero).orElseThrow(() ->
                new EntityNotFoundException("Não encontrado empregado relacionado ao projeto"));

        repository.deleteById(numero);
    }

    @Override
    public Page<EmpregadoProjeto> findFilter(EmpregadoProjeto filtro, Pageable pageable) {
        Example<EmpregadoProjeto> example = Example.of(new EmpregadoProjeto());

        return repository.findAll(getSpecFromDatesAndExample(filtro, example), pageable)
                .map(EmpregadoProjeto::new);
    }

    private Specification<EmpregadoProjeto> getSpecFromDatesAndExample(EmpregadoProjeto filtro, Example<EmpregadoProjeto> example) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            Optional<EmpregadoProjeto> optional = Optional.ofNullable(filtro);
            EmpregadoProjeto empregadoProjeto = optional.orElse(new EmpregadoProjeto());

            if (Objects.nonNull(empregadoProjeto.getNumero()))
                predicates.add(builder.equal(root.get("numero"), empregadoProjeto.getNumero()));

            if (Objects.nonNull(empregadoProjeto.getEmpregado()))
                predicates.add(builder.equal(root.get("empregado"), empregadoProjeto.getEmpregado()));

            if (Objects.nonNull(empregadoProjeto.getProjeto()))
                predicates.add(builder.equal(root.get("projeto"), empregadoProjeto.getProjeto()));

            if (Objects.nonNull(empregadoProjeto.getInicio()))
                predicates.add(builder.equal(root.get("inicio"), empregadoProjeto.getInicio()));

            if (Objects.nonNull(empregadoProjeto.getTermino()))
                predicates.add(builder.equal(root.get("termino"), empregadoProjeto.getTermino()));

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            int size = predicates.size();
            return builder.and(predicates.toArray(new Predicate[size]));
        };
    }

    private void validate(EmpregadoProjeto empregadoProjeto) {
        if (Objects.nonNull(empregadoProjeto.getEmpregado().getNumero()))
            empregadoRepository.findById(empregadoProjeto.getEmpregado().getNumero()).orElseThrow(() ->
                    new EntityNotFoundException("Empregado não existe"));

        if (Objects.nonNull(empregadoProjeto.getProjeto().getNumero()))
            projetoRepository.findById(empregadoProjeto.getProjeto().getNumero()).orElseThrow(() ->
                    new EntityNotFoundException("Projeto não existe"));

        if (empregadoProjeto.getInicio().isAfter(empregadoProjeto.getTermino()))
            throw new UnsupportedOperationException("Data do início do projeto não pode ser após a data de término");

        if (empregadoProjeto.getTermino().equals(empregadoProjeto.getInicio()))
            throw new UnsupportedOperationException("Data do término do projeto não pode ser igual a data de início");
    }

}
