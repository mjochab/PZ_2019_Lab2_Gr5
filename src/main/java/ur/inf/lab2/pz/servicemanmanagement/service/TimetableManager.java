package ur.inf.lab2.pz.servicemanmanagement.service;

import javafx.beans.property.ObjectProperty;
import jfxtras.scene.control.agenda.Agenda;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimetableManager {


    public Agenda load(Long leaderId) {

        Agenda agenda = new Agenda();
        agenda.displayedLocalDateTime().set(LocalDate.parse("2019-05-01").atTime(2, 10));
        Agenda.AppointmentGroupImpl taskGroup = new Agenda.AppointmentGroupImpl().withStyleClass("task");
        Agenda.AppointmentImplLocal task = new Agenda.AppointmentImplLocal()
                .withStartLocalDateTime(LocalDate.parse("2019-05-01").atTime(4, 00))
                .withEndLocalDateTime(LocalDate.parse("2019-05-01").atTime(15, 30))
                .withDescription("It's time")
                .withSummary("TO JEST ZADANIE KOLEŻKO")
                .withAppointmentGroup(taskGroup);

        agenda.appointments().addAll(
                task // you should use a map of AppointmentGroups
        );

        agenda.setOnDragDropped(val -> {
            System.out.println(val.getX());
        });

        agenda.newAppointmentCallbackProperty().set( (localDateTimeRange) -> {
            return new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(localDateTimeRange.getStartLocalDateTime())
                    .withEndLocalDateTime(localDateTimeRange.getEndLocalDateTime())
                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("task")); // it is better to have a map of appointment groups to get from
        });

//        agenda.setStyle("-fx-font-size: 40");
        agenda.getStyleClass().add("global-font");
        return agenda;
//        viewManager.getStae().getScene().getStylesheets().forEach(stylesheet -> agenda.getStylesheets().add(stylesheet));
    }
}
