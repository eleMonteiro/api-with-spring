package com.example.api.repositorys;

import com.example.api.models.Empregado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long>, JpaSpecificationExecutor<Empregado> {

    Optional<Empregado> findByCpf(String cpf);
}
