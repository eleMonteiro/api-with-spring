package com.example.GoFTecno.repositorys;

import java.util.Collection;
import java.util.Optional;

import com.example.GoFTecno.models.Departamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

    public Optional<Departamento> findByDnomeIgnoreCase(String dnome);

    public Collection<Departamento> findByGerenteIsNull();

}
