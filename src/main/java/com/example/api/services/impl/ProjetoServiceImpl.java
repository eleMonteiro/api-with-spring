package com.example.api.services.impl;

import com.example.api.models.Empregado;
import com.example.api.models.Exportar;
import com.example.api.models.Projeto;
import com.example.api.repositorys.ProjetoRepository;
import com.example.api.services.ProjetoService;
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
public class ProjetoServiceImpl implements ProjetoService {

    @Autowired
    private ProjetoRepository repository;

    @Override
    public Projeto save(Projeto projeto) {
        repository.findByNomeIgnoreCase(projeto.getNome()).ifPresent(p -> {
            throw new EntityExistsException("Projeto com nome " + p.getNome() + " já existe");
        });

        return repository.save(projeto);
    }

    @Override
    public Projeto update(Long numero, Projeto projeto) {
        Projeto proj = repository.findById(numero).orElseThrow(() ->
                new EntityNotFoundException("Projeto com identificador " + numero + " não encontrado"));

        if (!Objects.equals(numero, projeto.getNumero()))
            throw new UnsupportedOperationException("ID informado diferente do ID do Projeto.");

        repository.findByNomeIgnoreCase(projeto.getNome()).ifPresent(p -> {
            if (!Objects.equals(p.getNumero(), projeto.getNumero()))
                throw new EntityExistsException("Projeto com nome " + p.getNome() + " já existe");
        });

        projeto.setNumero(proj.getNumero());
        return repository.saveAndFlush(projeto);
    }

    @Override
    public void delete(Long numero) {
        repository.findById(numero).orElseThrow(() ->
                new EntityNotFoundException("Projeto com identificador " + numero + " não encontrado"));

        repository.deleteById(numero);
    }

    @Override
    public Page<Projeto> findFilter(Projeto filtro, Pageable pageable) {
        Example<Projeto> example = Example.of(new Projeto());

        return repository.findAll(getSpecFromDatesAndExample(filtro, example), pageable)
                .map(Projeto::new);

    }

    private Specification<Projeto> getSpecFromDatesAndExample(Projeto filtro, Example<Projeto> example) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            Optional optional = Optional.ofNullable(filtro);
            Projeto projeto = (Projeto) optional.orElse(new Projeto());

            if (Objects.nonNull(projeto.getNumero()))
                predicates.add(builder.equal(root.get("numero"), projeto.getNumero()));

            if (Objects.nonNull(projeto.getNome()))
                predicates.add(builder.like(builder.lower(root.get("nome")), "%" + projeto.getNome() + "%"));

            if (Objects.nonNull(projeto.getDepartamento()))
                predicates.add(builder.equal(root.get("departamento"), projeto.getDepartamento()));

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Override
    public void exportar(Exportar exportar, Pageable pageable) throws FileNotFoundException, JRException {
        Optional<Projeto> optional = Optional.ofNullable(exportar.getProjeto());
        Projeto projeto = optional.orElse(new Projeto());
        Page<Projeto> projetos = findFilter(projeto, pageable);

        String path = exportar.getCaminho() + exportar.getNomeArquivo() + "." + exportar.getFormato();
        String classPath = "classpath:templates-xml/projetos.jrxml";
        String header = "Empregados";

        if (exportar.getFormato().equalsIgnoreCase("pdf"))
            ExportUtils.exportReportPDF(path, classPath, header, projetos.getContent());
        if (exportar.getFormato().equalsIgnoreCase("xlsx"))
            ExportUtils.exportReportXLS(path, classPath, header, projetos.getContent());
    }
}
