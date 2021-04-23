package com.example.GoFTecno.repositorys;

import java.util.List;
import java.util.Optional;

import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.models.Empregado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, String> {

    public Optional<Empregado> findByEnomeIgnoreCase(String enome);

    @Query("SELECT e FROM  Empregado e WHERE e.cpf in ( SELECT DISTINCT s.supervisor FROM Empregado s WHERE s.supervisor IS NOT null)")
    public List<Empregado> findSupervisores();

    @Query("SELECT e FROM  Empregado e WHERE e.cpf NOT IN ( SELECT DISTINCT d.gerente FROM Departamento d WHERE d.gerente IS NOT null)")
    public List<Empregado> findNotGerentes();

    //@Query("SELECT e FROM  Empregado e WHERE e.departamento = ?1")
    public List<Empregado> findByDepartamento(Departamento departamento);
}
