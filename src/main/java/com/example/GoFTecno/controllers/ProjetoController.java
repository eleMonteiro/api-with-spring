package com.example.GoFTecno.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.GoFTecno.dtos.ProjetoDTO;
import com.example.GoFTecno.exceptions.ExceptionAdvice;
import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.models.Projeto;
import com.example.GoFTecno.services.ProjetoService;

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

@Tag(name = "Projeto")
@RestController
@RequestMapping(path = "/projetos")
public class ProjetoController extends ExceptionAdvice {

    @Autowired
    private ProjetoService service;

    @GetMapping({ "/all" })
    public List<ProjetoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping({ "/", "" })
    public Page<ProjetoDTO> findAll(@RequestParam Integer pagina, @RequestParam Integer tamanho) {
        return service.findAll(pagina, tamanho);
    }

    @GetMapping({ "/{id}" })
    public ProjetoDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping({ "/departamento" })
    public List<ProjetoDTO> findById(@RequestBody Departamento departamento) {
        return service.findByDepartamento(departamento);
    }

    @PostMapping({ "/", "" })
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProjetoDTO save(@Valid @RequestBody Projeto projeto) {
        return service.save(projeto);
    }

    @PutMapping({ "/{id}" })
    @ResponseStatus(code = HttpStatus.OK)
    public ProjetoDTO update(@PathVariable Integer id, @RequestBody Projeto projeto) {
        return service.update(id, projeto);
    }

    @DeleteMapping({ "/{id}" })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

}
