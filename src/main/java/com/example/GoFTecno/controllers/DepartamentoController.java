package com.example.GoFTecno.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.GoFTecno.dtos.DepartamentoDTO;
import com.example.GoFTecno.exceptions.ExceptionAdvice;
import com.example.GoFTecno.models.Departamento;
import com.example.GoFTecno.services.DepartamentoService;
import com.example.GoFTecno.utils.DepartamentoExcelExporter;

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

@Tag(name = "Departamento")
@RestController
@RequestMapping(path = "/departamentos")
public class DepartamentoController extends ExceptionAdvice {

    @Autowired
    private DepartamentoService service;

    @GetMapping({ "/all" })
    public List<DepartamentoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping({ "/", "" })
    public Page<DepartamentoDTO> findAll(@RequestParam Integer pagina, @RequestParam Integer tamanho) {
        return service.findAll(pagina, tamanho);
    }

    @GetMapping({ "/nome/{dnome}" })
    public DepartamentoDTO findAll(@PathVariable String dnome) {
        return service.findByDnome(dnome);
    }

    @GetMapping({ "/{id}" })
    public DepartamentoDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping({ "/empity-gerente" })
    public List<DepartamentoDTO> findByGerenteEmpity() {
        return service.findByGerenteIsNull();
    }

    @PostMapping({ "/", "" })
    @ResponseStatus(code = HttpStatus.CREATED)
    public DepartamentoDTO save(@Valid @RequestBody Departamento departamento) {
        return service.save(departamento);
    }

    @PutMapping({ "/{id}" })
    @ResponseStatus(code = HttpStatus.OK)
    public DepartamentoDTO update(@PathVariable Integer id, @RequestBody Departamento departamento) {
        return service.update(id, departamento);
    }

    @DeleteMapping({ "/{id}" })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping({ "/export" })
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=departamentos_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<DepartamentoDTO> departamentos = findAll();

        DepartamentoExcelExporter exporter = new DepartamentoExcelExporter(departamentos);
        exporter.export(response);
    }
}
