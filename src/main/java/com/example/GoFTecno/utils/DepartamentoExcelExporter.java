package com.example.GoFTecno.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.GoFTecno.dtos.DepartamentoDTO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DepartamentoExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<DepartamentoDTO> departamentos;

    public DepartamentoExcelExporter(List<DepartamentoDTO> departamentos) {
        this.departamentos = departamentos;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Departamentos");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Departamento Numero", style);
        createCell(row, 1, "Nome", style);
        createCell(row, 2, "Gerente CPF", style);
        createCell(row, 3, "Gerente Nome", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (DepartamentoDTO departamento : departamentos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, departamento.getDnumero(), style);
            createCell(row, columnCount++, departamento.getDnome(), style);

            if (departamento.getGerente() != null) {
                createCell(row, columnCount++, departamento.getGerente().getCpf(), style);
                createCell(row, columnCount++, departamento.getGerente().getEnome(), style);
            } else {
                createCell(row, columnCount++, null, style);
                createCell(row, columnCount++, null, style);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write((OutputStream) outputStream);
        workbook.close();

        outputStream.close();
    }
}