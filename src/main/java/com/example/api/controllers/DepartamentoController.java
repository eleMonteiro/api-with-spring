package com.example.api.controllers;

import com.example.api.exceptions.ExceptionAdvice;
import com.example.api.models.Departamento;
import com.example.api.services.impl.DepartamentoServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "Departamento")
@RestController
@RequestMapping(path = "/departamentos")
public class DepartamentoController extends ExceptionAdvice {

    @Autowired
    private DepartamentoServiceImpl service;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Departamento> findFilter(@RequestBody(required = false) Departamento filtro, Pageable pageable) {
        return service.findFilter(filtro, pageable);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Departamento> save(@Valid @RequestBody Departamento departamento) {
        Departamento salvo = service.save(departamento);

        URI uri =
                MvcUriComponentsBuilder
                        .fromController(getClass())
                        .buildAndExpand(salvo)
                        .toUri();

        return ResponseEntity.created(uri).body(salvo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Departamento> update(@PathVariable(name = "id") Long numero, @RequestBody Departamento departamento) {
        Departamento atualizado = service.update(numero, departamento);

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).buildAndExpand(atualizado)
                .toUri();

        return ResponseEntity.status(HttpStatus.OK).location(uri).body(atualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long numero) {
        service.delete(numero);
    }

}
