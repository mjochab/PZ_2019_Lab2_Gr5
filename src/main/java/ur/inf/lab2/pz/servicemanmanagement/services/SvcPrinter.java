package ur.inf.lab2.pz.servicemanmanagement.services;

import net.sf.jasperreports.engine.JRException;
import ur.inf.lab2.pz.servicemanmanagement.config.reportgenerator.TaskReport;
import ur.inf.lab2.pz.servicemanmanagement.config.reportgenerator.TaskReportGenerator;
import ur.inf.lab2.pz.servicemanmanagement.timetable.AllocatedTaskRaportPrinter;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;

/**
 * Klasa wykorzystywana do obsługi drukowania raportów
 */
public class SvcPrinter implements AllocatedTaskRaportPrinter {
    @Override
    public void print(AllocatedTask taskToPrint, String absolutePath) {
        TaskReportGenerator generator = new TaskReportGenerator();
        try {
            generator.generateTaskReport(toReport(taskToPrint), absolutePath);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private TaskReport toReport(AllocatedTask taskToPrint) {
        TaskReportImpl taskReport = new TaskReportImpl();
        taskReport.teamLeader = "Jan Kowalski";
        taskReport.title = taskToPrint.getTag();
        taskReport.details = taskToPrint.getDetails();
        taskReport.clientFirstname = taskToPrint.getClientData().getFirstname();
        taskReport.clientLastname = taskToPrint.getClientData().getSurname();
        taskReport.clientPhoneNumber = taskToPrint.getClientData().getPhoneNumber();
        taskReport.clientStreet = taskToPrint.getClientData().getStreet();
        taskReport.clientHouseNumber = taskToPrint.getClientData().getHouseNumber();
        taskReport.clientApartment = taskToPrint.getClientData().getFlatNumber();
        taskReport.clientCity = taskToPrint.getClientData().getCity();
        return taskReport;
    }

    private class TaskReportImpl implements TaskReport {

        public String teamLeader;
        public String title;
        public String details;
        public String clientFirstname;
        public String clientLastname;
        public String clientPhoneNumber;
        public String clientStreet;
        public String clientHouseNumber;
        public String clientApartment;
        public String clientCity;

        @Override
        public String getTeamLeader() {
            return teamLeader;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getDetails() {
            return details;
        }

        @Override
        public String getClientFirstName() {
            return clientFirstname;
        }

        @Override
        public String getClientLastName() {
            return clientLastname;
        }

        @Override
        public String getClientPhoneNumber() {
            return clientPhoneNumber;
        }

        @Override
        public String getClientStreet() {
            return clientStreet;
        }

        @Override
        public String getClientHouseNumber() {
            return clientHouseNumber;
        }

        @Override
        public String getClientApartmentNumber() {
            return clientApartment;
        }

        @Override
        public String getClientCity() {
            return clientCity;
        }
    }
}
