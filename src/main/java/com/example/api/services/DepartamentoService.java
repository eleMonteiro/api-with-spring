package com.example.api.services;

import com.example.api.models.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartamentoService extends GenericService<Departamento>, ExportacaoService {

    Page<Departamento> search(String search, Pageable pageable);

}
