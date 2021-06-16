package com.example.api.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportUtils {

    public static void exportReportPDF(String path, String classPath, String header, List<?> objects) throws FileNotFoundException, JRException {
        JasperPrint jasperPrint = getJasperPrint(classPath, header, objects);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path);
    }

    public static void exportReportXLS(String path, String classPath, String header, List<?> objects) throws FileNotFoundException, JRException {
        JasperPrint jasperPrint = getJasperPrint(classPath, header, objects);

        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
        exporter.exportReport();
    }

    private static JasperPrint getJasperPrint(String classPath, String header, List<?> objects) throws JRException, FileNotFoundException {
        File file = ResourceUtils.getFile(classPath);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(objects);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", header);

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }


}
