package com.example.api.services.impl;

import com.example.api.models.Empregado;
import com.example.api.models.Exportar;
import com.example.api.repositorys.DepartamentoRepository;
import com.example.api.repositorys.EmpregadoRepository;
import com.example.api.services.EmpregadoService;
import com.example.api.utils.ExportUtils;
import net.sf.jasperreports.engine.JRException;
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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmpregadoServiceImpl implements EmpregadoService {

    private final EmpregadoRepository repository;

    private final DepartamentoRepository departamentoRepository;

    @Autowired
    public EmpregadoServiceImpl(EmpregadoRepository repository, DepartamentoRepository departamentoRepository) {
        this.repository = repository;
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public Empregado save(Empregado empregado) {

        repository.findByCpf(empregado.getCpf()).ifPresent(p -> {
            throw new EntityExistsException("Empregado com CPF passado já existe");
        });

        if (Objects.nonNull(empregado.getDepartamento().getNumero()))
            departamentoRepository.findById(empregado.getDepartamento().getNumero()).orElseThrow(() ->
                    new EntityNotFoundException("Departamento não existe"));

        return repository.save(empregado);
    }

    @Override
    public Empregado update(Long numero, Empregado empregado) {
        Empregado emp = repository.findById(numero).orElseThrow(() ->
                new EntityNotFoundException("Empregado com identificador " + numero + " não encontrado"));

        if (Objects.nonNull(empregado.getDepartamento().getNumero()))
            departamentoRepository.findById(empregado.getDepartamento().getNumero()).orElseThrow(() ->
                    new EntityNotFoundException("Departamento não existe"));

        if (!Objects.equals(numero, empregado.getNumero()))
            throw new UnsupportedOperationException("ID informado diferente do ID do Empregado.");

        repository.findByCpf(empregado.getCpf()).ifPresent(d -> {
            if (!Objects.equals(d.getCpf(), empregado.getCpf()))
                throw new EntityExistsException("Empregado com CPF passado já existe");
        });

        empregado.setNumero(emp.getNumero());
        return repository.saveAndFlush(empregado);
    }

    @Override
    public void delete(Long numero) {
        repository.findById(numero).orElseThrow(() ->
                new EntityNotFoundException("Empregado com identificador " + numero + " não encontrado"));

        repository.deleteById(numero);
    }

    @Override
    public Page<Empregado> findFilter(Empregado filtro, Pageable pageable) {
        Example<Empregado> example = Example.of(new Empregado());

        return repository.findAll(getSpecFromDatesAndExample(filtro, example), pageable)
                .map(Empregado::new);
    }

    private Specification<Empregado> getSpecFromDatesAndExample(Empregado filtro, Example<Empregado> example) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            Optional<Empregado> optional = Optional.ofNullable(filtro);
            Empregado empregado = optional.orElse(new Empregado());

            if (Objects.nonNull(empregado.getNumero()))
                predicates.add(builder.equal(root.get("numero"), empregado.getNumero()));

            if (Objects.nonNull(empregado.getCpf()))
                predicates.add(builder.equal(root.get("cpf"), empregado.getCpf()));

            if (Objects.nonNull(empregado.getSalario()))
                predicates.add(builder.equal(root.get("salario"), empregado.getSalario()));

            if (Objects.nonNull(empregado.getDepartamento()))
                predicates.add(builder.equal(root.get("departamento"), empregado.getDepartamento()));

            if (Objects.nonNull(empregado.getNome()))
                predicates.add(builder.like(builder.lower(root.get("nome")), "%" + empregado.getNome() + "%"));

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            int size = predicates.size();
            return builder.and(predicates.toArray(new Predicate[size]));
        };
    }

    @Override
    public void exportar(Exportar exportar, Pageable pageable) throws FileNotFoundException, JRException {
        Optional<Empregado> optional = Optional.ofNullable(exportar.getEmpregado());
        Empregado empregado = optional.orElse(new Empregado());
        Page<Empregado> empregados = findFilter(empregado, pageable);

        String path = exportar.getCaminho() + exportar.getNomeArquivo() + "." + exportar.getFormato();
        String classPath = "classpath:templates-xml/empregados.jrxml";
        String header = "Empregados";

        if (exportar.getFormato().equalsIgnoreCase("pdf"))
            ExportUtils.exportReportPDF(path, classPath, header, empregados.getContent());
        if (exportar.getFormato().equalsIgnoreCase("xlsx"))
            ExportUtils.exportReportXLS(path, classPath, header, empregados.getContent());
    }
}
