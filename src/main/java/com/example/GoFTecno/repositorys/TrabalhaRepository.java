package com.example.GoFTecno.repositorys;

import java.util.List;

import com.example.GoFTecno.models.Empregado;
import com.example.GoFTecno.models.Projeto;
import com.example.GoFTecno.models.Trabalha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabalhaRepository extends JpaRepository<Trabalha, Integer> {

    @Query("SELECT t FROM  Trabalha t WHERE t.empregado = ?1")
    public List<Trabalha> findByEmpregado(Empregado empregado);

    @Query("SELECT t FROM  Trabalha t WHERE t.projeto = ?1")
    public List<Trabalha> findByProjeto(Projeto projeto);
    
}
