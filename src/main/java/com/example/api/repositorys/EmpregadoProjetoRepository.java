package com.example.api.repositorys;

import com.example.api.models.EmpregadoProjeto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpregadoProjetoRepository extends JpaRepository<EmpregadoProjeto, Long>, JpaSpecificationExecutor<EmpregadoProjeto> {


}
