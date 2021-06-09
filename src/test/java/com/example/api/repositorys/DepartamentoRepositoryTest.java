package com.example.api.repositorys;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import com.example.api.ConfigIntegration;
import com.example.api.models.Departamento;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DepartamentoRepositoryTest extends ConfigIntegration {

    @Autowired
    private DepartamentoRepository repository;

    @BeforeEach
    public void setUp() {
        registryEntity(Departamento.class);
    }

    @AfterEach
    public void tearDown() {
        clearTables();
    }

    private Departamento departamentoCompleto() {
        Departamento departamento = new Departamento();
        departamento.setDnome("dnome");

        return departamento;
    }

    @Test
    public void autowiredNotNull() {
        assertNotNull(repository);
    }

    @Test
    public void deveriaSalvarDepartamento() {
        Departamento departamento = departamentoCompleto();
        assertThat(repository.save(departamento)).isNotNull();
    }

    @Test
    public void deveriaNaoSalvarDepartamentoComNomeNulo() {
        Departamento departamento = departamentoCompleto();
        departamento.setDnome(null);
        assertThrows(ConstraintViolationException.class, () -> repository.save(departamento));
    }

    @Test
    public void deveriaEditarDepartamento() {
        Departamento departamento = departamentoCompleto();
        Departamento esperado = repository.save(departamento);

        assertThat(esperado).isNotNull();

        esperado.setDnome("Departamento");
        Departamento atual = repository.save(esperado);

        assertThat(atual).isEqualTo(esperado);
        assertThat(repository.findByDnomeIgnoreCase(atual.getDnome()).isPresent()).isTrue();
    }

    @Test
    public void deveriaApagarDepartamento() {
        Departamento departamento = departamentoCompleto();
        Departamento esperado = repository.save(departamento);

        assertThat(esperado).isNotNull();

        repository.deleteById(esperado.getDnumero());

        Boolean atual = repository.findByDnomeIgnoreCase(esperado.getDnome()).isPresent();

        assertThat(atual).isFalse();
    }

    @Test
    public void deveriaNaoApagarDepartamentoInexistente() {
        Departamento departamento = departamentoCompleto();
        departamento.setDnome("RH");
        Departamento esperado = repository.save(departamento);

        assertThat(esperado).isNotNull();

        assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(-1));
    }

}
