package ur.inf.lab2.pz.servicemanmanagement.service;

import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.TimetableTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class MockTaskService implements TimetableDatasource {

    class SimpleTimetableTask implements TimetableTask {

        private String id;
        private String tag;
        private String description;
        private LocalDateTime dateTimeFrom;
        private LocalDateTime dateTimeTo;

        public SimpleTimetableTask(String id, String tag, String description, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
            this.id = id;
            this.tag = tag;
            this.description = description;
            this.dateTimeFrom = dateTimeFrom;
            this.dateTimeTo = dateTimeTo;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public LocalDateTime getDateTimeFrom() {
            return dateTimeFrom;
        }

        @Override
        public LocalDateTime getDateTimeTo() {
            return dateTimeTo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleTimetableTask that = (SimpleTimetableTask) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    @Override
    public Set<TimetableTask> getTasksByDateRange(Long leaderId, DateRange peroidOfTime) {
        TimetableTask simpleTask1 = new SimpleTimetableTask(
                "M1",
                "Montaż",
                "Pojechać, zniszczyć i wrócić",
                LocalDate.parse("2019-05-14").atTime(12, 0),
                LocalDate.parse("2019-05-14").atTime(13, 30));
        TimetableTask simpleTask2 = new SimpleTimetableTask(
                "M2",
                "Montaż",
                "Montaż aparatury do bimbru",
                LocalDate.parse("2019-05-14").atTime(14, 0),
                LocalDate.parse("2019-05-14").atTime(16, 30));
        TimetableTask simpleTask3 = new SimpleTimetableTask(
                "R1",
                "Rozbiórka",
                "Rozebrać siostrę klienta",
                LocalDate.parse("2019-05-15").atTime(9, 0),
                LocalDate.parse("2019-05-15").atTime(10, 0));

        Set<TimetableTask> tasks = new HashSet<>();
        tasks.addAll(Arrays.asList(simpleTask1, simpleTask2, simpleTask3));
        return tasks;
    }

    @Override
    public Set<TimetableTask> getUnallocatedTasks() {
        TimetableTask simpleTask1 = new SimpleTimetableTask(
                "N1",
                "Nieprzydzielone",
                "Hejka",
                null,
                null);
        TimetableTask simpleTask2 = new SimpleTimetableTask(
                "N2",
                "Nieprzydzielone",
                "Hejka2",
                null,
                null);
        Set<TimetableTask> tasks = new HashSet<>();
        tasks.addAll(Arrays.asList(simpleTask1, simpleTask2));
        return tasks;
    }
}
