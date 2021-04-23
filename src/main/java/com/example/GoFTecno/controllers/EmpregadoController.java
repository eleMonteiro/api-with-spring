package com.example.GoFTecno.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.GoFTecno.dtos.EmpregadoDTO;
import com.example.GoFTecno.exceptions.ExceptionAdvice;
import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.models.Empregado;
import com.example.GoFTecno.services.EmpregadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Empregado")
@RestController
@RequestMapping(path = "/empregados")
public class EmpregadoController extends ExceptionAdvice {

    @Autowired
    private EmpregadoService service;

    @GetMapping({ "/all" })
    public List<EmpregadoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping({ "/", "" })
    public Page<EmpregadoDTO> findAll(@RequestParam Integer pagina, @RequestParam Integer tamanho) {
        return service.findAll(pagina, tamanho);
    }

    @GetMapping({ "/{cpf}" })
    public EmpregadoDTO findById(@PathVariable String cpf) {
        return service.findById(cpf);
    }

    @GetMapping({ "/departamento" })
    public List<EmpregadoDTO> findById(@RequestBody Departamento departamento) {
        return service.findByDepartamento(departamento);
    }

    @GetMapping({ "/supervisores" })
    public List<EmpregadoDTO> findSupervisores() {
        return service.findSupervisores();
    }

    @GetMapping({ "/nao-gerentes" })
    public List<EmpregadoDTO> findNotGerentes() {
        return service.findNotGerentes();
    }

    @PostMapping({ "/", "" })
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmpregadoDTO save(@Valid @RequestBody Empregado empregado) {
        return service.save(empregado);
    }

    @PutMapping({ "/{cpf}" })
    @ResponseStatus(code = HttpStatus.OK)
    public EmpregadoDTO update(@PathVariable String cpf, @RequestBody Empregado empregado) {
        return service.update(cpf, empregado);
    }

    @DeleteMapping({ "/{cpf}" })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String cpf) {
        service.delete(cpf);
    }

}
