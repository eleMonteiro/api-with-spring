package com.example.api.services;

import com.example.api.models.Exportar;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;

public interface ExportacaoService {

    void exportar(Exportar exportar, Pageable pageable) throws FileNotFoundException, JRException;

}
