package com.example.GoFTecno.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.GoFTecno.dtos.TrabalhaDTO;
import com.example.GoFTecno.exceptions.ExceptionAdvice;
import com.example.GoFTecno.models.Empregado;
import com.example.GoFTecno.models.Projeto;
import com.example.GoFTecno.models.Trabalha;
import com.example.GoFTecno.services.TrabalhaService;

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

@Tag(name = "Trabalho")
@RestController
@RequestMapping(path = "/trabalhos")
public class TrabalhaController extends ExceptionAdvice {

    @Autowired
    private TrabalhaService service;

    @GetMapping({ "/all" })
    public List<TrabalhaDTO> findAll() {
        return service.findAll();
    }

    @GetMapping({ "/", "" })
    public Page<TrabalhaDTO> findAll(@RequestParam Integer pagina, @RequestParam Integer tamanho) {
        return service.findAll(pagina, tamanho);
    }

    @GetMapping({ "/{id}" })
    public TrabalhaDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping({ "/empregado" })
    public List<TrabalhaDTO> findByEmpregado(@RequestBody Empregado empregado) {
        return service.findByEmpregado(empregado);
    }

    @GetMapping({ "/projeto" })
    public List<TrabalhaDTO> findByProjeto(@RequestBody Projeto projeto) {
        return service.findByProjeto(projeto);
    }

    @PostMapping({ "/", "" })
    @ResponseStatus(code = HttpStatus.CREATED)
    public TrabalhaDTO save(@Valid @RequestBody Trabalha trabalha) {
        return service.save(trabalha);
    }

    @PutMapping({ "/{id}" })
    @ResponseStatus(code = HttpStatus.OK)
    public TrabalhaDTO update(@PathVariable Integer id, @RequestBody Trabalha trabalha) {
        return service.update(id, trabalha);
    }

    @DeleteMapping({ "/{id}" })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

}
