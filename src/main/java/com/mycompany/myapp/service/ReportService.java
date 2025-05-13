package com.mycompany.myapp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class ReportService {

    public byte[] exportToPdf(List<?> lista, File reporte, HashMap<String, Object> parameters) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(lista, reporte, parameters));
    }

    public byte[] exportToXls(List<?> lista, File reporte, HashMap<String, Object> parameters) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setExporterInput(new SimpleExporterInput(getReport(lista, reporte, parameters)));

        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private JasperPrint getReport(List<?> lista, File reporte, HashMap<String, Object> parameters)
        throws FileNotFoundException, JRException {
        final JasperReport report = (JasperReport) JRLoader.loadObject(reporte);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource((Collection<?>) lista));

        return jasperPrint;
    }
}
