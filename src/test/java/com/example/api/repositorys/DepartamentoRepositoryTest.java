package com.example.api.repositorys;

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
    void setUp() {
        registryEntity(Departamento.class);
    }

    @AfterEach
    void tearDown() {
        clearTables();
    }

    private Departamento departamentoCompleto() {
        Departamento departamento = new Departamento();
        departamento.setNome("dnome");

        return departamento;
    }

    @Test
    public void deveriaSalvarDepartamento() {
        Departamento departamento = departamentoCompleto();
        assertThat(repository.save(departamento)).isNotNull();
    }

    @Test
    public void deveriaNaoSalvarDepartamentoComNomeNulo() {
        Departamento departamento = departamentoCompleto();
        departamento.setNome(null);
        assertThrows(ConstraintViolationException.class, () -> repository.save(departamento));
    }

    @Test
    public void deveriaEditarDepartamento() {
        Departamento departamento = departamentoCompleto();
        Departamento esperado = repository.save(departamento);

        assertThat(esperado).isNotNull();

        esperado.setNome("Departamento");
        Departamento atual = repository.save(esperado);

        assertThat(atual).isEqualTo(esperado);
        assertThat(repository.findByNomeIgnoreCase(atual.getNome()).isPresent()).isTrue();
    }

    @Test
    public void deveriaApagarDepartamento() {
        Departamento departamento = departamentoCompleto();
        Departamento esperado = repository.save(departamento);

        assertThat(esperado).isNotNull();

        repository.deleteById(esperado.getNumero());

        Boolean atual = repository.findByNomeIgnoreCase(esperado.getNome()).isPresent();

        assertThat(atual).isFalse();
    }

    @Test
    public void deveriaNaoApagarDepartamentoInexistente() {
        Departamento departamento = departamentoCompleto();
        departamento.setNome("RH");
        Departamento esperado = repository.save(departamento);

        assertThat(esperado).isNotNull();

        assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(-1L));
    }

}
