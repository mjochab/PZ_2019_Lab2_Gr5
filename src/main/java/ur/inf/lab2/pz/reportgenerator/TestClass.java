package ur.inf.lab2.pz.reportgenerator;

import net.sf.jasperreports.engine.JRException;

public class TestClass {

    public static void main(String[] args) throws JRException {


        TaskReportImpl report = new TaskReportImpl("a","b","c",
                "d","e","f","g","h","j","k");
        TaskReportGenerator.generateTaskReport(report,"/home/damian/Pobrane/output.pdf");
    }
}
