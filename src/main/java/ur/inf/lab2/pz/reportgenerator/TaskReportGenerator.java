package ur.inf.lab2.pz.reportgenerator;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TaskReportGenerator {

    public static void generateTaskReport(TaskReportImpl task, String pathToFile) throws JRException {

        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/jrxml/report.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title",task.getTitle());
        parameters.put("teamLeader",task.getTeamLeader());
        parameters.put("details",task.getDetails());
        parameters.put("clientFirstName",task.getClientFirstName());
        parameters.put("clientLastName",task.getClientLastName());
        parameters.put("clientStreet",task.getClientStreet());
        parameters.put("clientHouseNumber",task.getClientHouseNumber());
        parameters.put("clientApartmentNumber",task.getClientApartmentNumber());
        parameters.put("clientCity",task.getClientCity());
        parameters.put("clientPhoneNumber",task.getClientPhoneNumber());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JREmptyDataSource());

        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(pathToFile));

        SimplePdfReportConfiguration reportConfig
                = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig
                = new SimplePdfExporterConfiguration();
        exportConfig.setEncrypted(true);

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        exporter.exportReport();
    }
}
