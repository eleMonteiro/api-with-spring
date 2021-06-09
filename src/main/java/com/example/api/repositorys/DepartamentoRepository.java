package com.example.api.repositorys;

import com.example.api.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long>, JpaSpecificationExecutor<Departamento> {

    Optional<Departamento> findByNomeIgnoreCase(String nome);

}
