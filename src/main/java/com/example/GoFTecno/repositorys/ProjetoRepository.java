package com.example.GoFTecno.repositorys;

import java.util.List;

import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.models.Projeto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    @Query("SELECT p FROM  Projeto p WHERE p.departamento = ?1")
    public List<Projeto> findByDepartamento(Departamento departamento);

}
