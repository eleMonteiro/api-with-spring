package com.example.GoFTecno.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.GoFTecno.dtos.EmpregadoDTO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmpregadoExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<EmpregadoDTO> empregados;

    public EmpregadoExcelExporter(List<EmpregadoDTO> empregados) {
        this.empregados = empregados;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Empregados");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        createCell(row, 0, "Empregado Numero", style);
        createCell(row, 1, "Empregado Nome", style);
        createCell(row, 2, "Supervisor CPF", style);
        createCell(row, 3, "Supervisor Nome", style);
        createCell(row, 4, "Departamento Numero", style);
        createCell(row, 5, "Departamento Nome", style);
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
        font.setFontHeight(11);
        style.setFont(font);

        for (EmpregadoDTO empregado : empregados) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, empregado.getCpf(), style);
            createCell(row, columnCount++, empregado.getEnome(), style);

            if (empregado.getSupervisor() != null) {
                createCell(row, columnCount++, empregado.getSupervisor().getCpf(), style);
                createCell(row, columnCount++, empregado.getSupervisor().getEnome(), style);
            } else {
                createCell(row, columnCount++, null, style);
                createCell(row, columnCount++, null, style);
            }

            if (empregado.getDepartamento() != null) {
                createCell(row, columnCount++, empregado.getDepartamento().getDnumero(), style);
                createCell(row, columnCount++, empregado.getDepartamento().getDnome(), style);
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