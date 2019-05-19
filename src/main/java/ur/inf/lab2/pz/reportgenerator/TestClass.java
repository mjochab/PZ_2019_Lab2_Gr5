package ur.inf.lab2.pz.reportgenerator;

import net.sf.jasperreports.engine.JRException;

public class TestClass {

    public static void main(String[] args) throws JRException {


        TaskReportImpl report = new TaskReportImpl("Przykladowa godnosc pracownika","Przykladowy tytul zadania","Przykladowy dodatkowy opis zadania",
                "Przykladowe imie klienta","Przykladowe nazwisko klienta","Przykladowy nr telefonu","Przykladowa ulica","Przykladowy nr domu","Przykladowy nr mieszkania","Przykladowe miasto");
        TaskReportGenerator.generateTaskReport(report,"/home/irek/Pobrane/output.pdf");
    }
}
