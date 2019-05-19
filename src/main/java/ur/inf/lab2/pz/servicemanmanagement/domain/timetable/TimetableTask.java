package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import java.time.LocalDateTime;

public interface TimetableTask {

    String getId(); // np. M1  (Pierwsza litera taga + numer, następny task z tagiem montaż byłby M2)
    String getTag(); //np. Montaż
    String getDescription();
    LocalDateTime getDateTimeFrom(); //example of construct LocalDate.parse("2019-05-01").atTime(12, 00);
    LocalDateTime getDateTimeTo(); // LocalDate.parse("2019-05-01").atTime(14, 30);

    // przeslon equals i hashcode po id, najlepiej wygeneruj InteliJem
}
