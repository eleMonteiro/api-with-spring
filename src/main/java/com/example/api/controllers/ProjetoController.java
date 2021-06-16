package com.example.api.controllers;

import com.example.api.exceptions.ExceptionAdvice;
import com.example.api.models.Exportar;
import com.example.api.models.Projeto;
import com.example.api.services.ProjetoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.URI;

@Tag(name = "Projeto")
@RestController
@RequestMapping(path = "/projetos")
public class ProjetoController extends ExceptionAdvice {

    private final ProjetoService service;

    @Autowired
    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Projeto> findFilter(@RequestBody(required = false) Projeto filtro, Pageable pageable) {
        return service.findFilter(filtro, pageable);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Projeto> save(@Valid @RequestBody Projeto projeto) {
        Projeto salvo = service.save(projeto);

        URI uri =
                MvcUriComponentsBuilder
                        .fromController(getClass())
                        .buildAndExpand(salvo)
                        .toUri();

        return ResponseEntity.created(uri).body(salvo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Projeto update(@PathVariable(name = "id") Long numero, @RequestBody Projeto projeto) {
        return service.update(numero, projeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long numero) {
        service.delete(numero);
    }

    @GetMapping("/exportar")
    @ResponseStatus(code = HttpStatus.OK)
    public void exportar(@RequestBody Exportar exportar, Pageable pageable) throws JRException, FileNotFoundException {
        service.exportar(exportar, pageable);
    }
}
