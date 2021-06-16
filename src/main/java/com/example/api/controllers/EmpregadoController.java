package com.example.api.controllers;

import com.example.api.exceptions.ExceptionAdvice;
import com.example.api.models.Empregado;
import com.example.api.models.Exportar;
import com.example.api.services.EmpregadoService;
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

@Tag(name = "Empregado")
@RestController
@RequestMapping(path = "/empregados")
public class EmpregadoController extends ExceptionAdvice {

    private final EmpregadoService service;

    @Autowired
    public EmpregadoController(EmpregadoService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Empregado> findFilter(@RequestBody(required = false) Empregado filtro, Pageable pageable) {
        return service.findFilter(filtro, pageable);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Empregado> save(@Valid @RequestBody Empregado empregado) {
        Empregado salvo = service.save(empregado);

        URI uri =
                MvcUriComponentsBuilder
                        .fromController(getClass())
                        .buildAndExpand(salvo)
                        .toUri();

        return ResponseEntity.created(uri).body(salvo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Empregado update(@PathVariable(name = "id") Long numero, @RequestBody Empregado empregado) {
        return service.update(numero, empregado);
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
