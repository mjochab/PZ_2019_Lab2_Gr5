package ur.inf.lab2.pz.servicemanmanagement.service;

import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.UnallocatedTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class MockTaskService implements TimetableDatasource {

    @Override
    public Set<AllocatedTask> getAllocatedTasks(Long leaderId) {
        AllocatedTask simpleTask0 = new SimpleTimetableTask( //TODO wholeday
                "M1",
                "Montaż",
                "Pojechać, zniszczyć i wrócić",
                LocalDate.parse("2019-05-19").atTime(9, 0),
                LocalDate.parse("2019-05-19").atTime(11, 50));
        AllocatedTask simpleTask1 = new SimpleTimetableTask(
                "M2",
                "Montaż",
                "Pojechać, zniszczyć i wrócić",
                LocalDate.parse("2019-05-20").atTime(12, 0),
                LocalDate.parse("2019-05-20").atTime(13, 30));
        AllocatedTask simpleTask2 = new SimpleTimetableTask(
                "M3",
                "Montaż",
                "Montaż aparatury do bimbru",
                LocalDate.parse("2019-05-21").atTime(14, 0),
                LocalDate.parse("2019-05-21").atTime(16, 30));
        AllocatedTask simpleTask3 = new SimpleTimetableTask(
                "R1",
                "Rozbiórka",
                "Rozebrać siostrę klienta",
                LocalDate.parse("2019-05-21").atTime(9, 0),
                LocalDate.parse("2019-05-21").atTime(10, 0));

        Set<AllocatedTask> tasks = new HashSet<>();
        tasks.addAll(Arrays.asList(simpleTask0, simpleTask1, simpleTask2, simpleTask3));
        return tasks;
    }

    @Override
    public Set<UnallocatedTask> getUnallocatedTasks() {
        UnallocatedTask simpleTask1 = new SimpleUnallocatedTask(
                "N1",
                "Nieprzydzielone",
                "Hejka");
        UnallocatedTask simpleTask2 = new SimpleUnallocatedTask(
                "N2",
                "Nieprzydzielone",
                "Hejka2");
        Set<UnallocatedTask> tasks = new HashSet<>();
        tasks.addAll(Arrays.asList(simpleTask1, simpleTask2));
        return tasks;
    }

    @Override
    public void saveAllocated(Collection<AllocatedTask> allocatedTasks) {
        System.out.println("Saving " + allocatedTasks.size() + " allocatedTasks");
    }

    @Override
    public void saveUnallocated(Collection<UnallocatedTask> unallocatedTasks) {
        System.out.println("Saving " + unallocatedTasks.size() + " unallocatedTasks");
    }

    private class SimpleUnallocatedTask implements UnallocatedTask {
        private String id;
        private String tag;
        private String description;

        public SimpleUnallocatedTask(String id, String tag, String description) {
            this.id = id;
            this.tag = tag;
            this.description = description;
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
    }

    private class SimpleTimetableTask implements AllocatedTask {
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

}
