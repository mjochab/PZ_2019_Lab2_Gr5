package ur.inf.lab2.pz.servicemanmanagement.service;

import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.ClientData;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.TaskState;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.UnallocatedTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class MockTaskService implements TimetableDatasource {

    @Override
    public Set<AllocatedTask> getAllocatedTasks(Long leaderId) {
        AllocatedTask simpleTask0 = new SimpleTimetableTask(
                "M1",
                "Montaż",
                "Pojechać, zniszczyć i wrócić",
                LocalDate.parse("2019-05-19").atTime(9, 0),
                LocalDate.parse("2019-05-19").atTime(11, 50),
                new MockClientData(
                        "m1-montaż",
                        "m1-montaż",
                        "m1-montaż",
                        "m1-montaż",
                        "m1-montaż",
                        "m1-montaż",
                        "m1-montaż"),
                false,
                TaskState.TODO);
        AllocatedTask simpleTask1 = new SimpleTimetableTask(
                "M2",
                "Montaż",
                "Pojechać, zniszczyć i wrócić",
                LocalDate.parse("2019-05-20").atTime(12, 0),
                LocalDate.parse("2019-05-20").atTime(13, 30),
                new MockClientData(
                        "m2-montaż",
                        "m2-montaż",
                        "m2-montaż",
                        "m2-montaż",
                        "m2-montaż",
                        "m2-montaż",
                        "m2-montaż"),
                true,
                TaskState.TODO);
        AllocatedTask simpleTask2 = new SimpleTimetableTask(
                "M3",
                "Montaż",
                "Montaż aparatury do bimbru",
                LocalDate.parse("2019-05-21").atTime(14, 0),
                LocalDate.parse("2019-05-21").atTime(16, 30),
                new MockClientData(
                        "m3-montaż",
                        "m3-montaż",
                        "m3-montaż",
                        "m3-montaż",
                        "m3-montaż",
                        "m3-montaż",
                        "m3-montaż"),
                false,
                TaskState.DONE);
        AllocatedTask simpleTask3 = new SimpleTimetableTask(
                "R1",
                "Rozbiórka",
                "Rozebrać siostrę klienta",
                LocalDate.parse("2019-05-21").atTime(9, 0),
                LocalDate.parse("2019-05-21").atTime(10, 0),
                new MockClientData(
                        "r1-rozbiorka",
                        "r1-rozbiorka",
                        "r1-rozbiorka",
                        "r1-rozbiorka",
                        "r1-rozbiorka",
                        "r1-rozbiorka",
                        "r1-rozbiorka"),
                false,
                TaskState.TODO);

        Set<AllocatedTask> tasks = new HashSet<>();
        tasks.addAll(Arrays.asList(simpleTask0, simpleTask1, simpleTask2, simpleTask3));
        return tasks;
    }

    @Override
    public Set<UnallocatedTask> getUnallocatedTasks() {
        UnallocatedTask simpleTask1 = new SimpleUnallocatedTask(
                "N1",
                "Nieprzydzielone",
                "Hejka",
                new MockClientData(
                        "n1-nieprzydzielone",
                        "n1-nieprzydzielone",
                        "n1-nieprzydzielone",
                        "n1-nieprzydzielone",
                        "n1-nieprzydzielone",
                        "n1-nieprzydzielone",
                        "n1-nieprzydzielone"));
        UnallocatedTask simpleTask2 = new SimpleUnallocatedTask(
                "N2",
                "Nieprzydzielone",
                "Hejka2",
                new MockClientData(
                        "n2-nieprzydzielone",
                        "n2-nieprzydzielone",
                        "n2-nieprzydzielone",
                        "n2-nieprzydzielone",
                        "n2-nieprzydzielone",
                        "n2-nieprzydzielone",
                        "n2-nieprzydzielone"));
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


    private class MockClientData implements ClientData {

        private String firstname;
        private String surname;
        private String phoneNumber;
        private String street;
        private String houseNumber;
        private String flatNumber;
        private String city;

        public MockClientData(String firstname, String surname, String phoneNumber, String street, String houseNumber, String flatNumber, String city) {
            this.firstname = firstname;
            this.surname = surname;
            this.phoneNumber = phoneNumber;
            this.street = street;
            this.houseNumber = houseNumber;
            this.flatNumber = flatNumber;
            this.city = city;
        }

        @Override
        public String getFirstname() {
            return firstname;
        }

        @Override
        public String getSurname() {
            return surname;
        }

        @Override
        public String getPhoneNumber() {
            return phoneNumber;
        }

        @Override
        public String getStreet() {
            return street;
        }

        @Override
        public String getHouseNumber() {
            return houseNumber;
        }

        @Override
        public String getFlatNumber() {
            return flatNumber;
        }

        @Override
        public String getCity() {
            return city;
        }
    }

    private class SimpleUnallocatedTask implements UnallocatedTask {
        private String id;
        private String tag;
        private String description;
        private ClientData clientData;

        public SimpleUnallocatedTask(String id, String tag, String description, ClientData clientData) {
            this.id = id;
            this.tag = tag;
            this.description = description;
            this.clientData = clientData;
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
        public ClientData getClientData() {
            return clientData;
        }
    }

    private class SimpleTimetableTask implements AllocatedTask {
        private String id;
        private String tag;
        private String description;
        private LocalDateTime dateTimeFrom;
        private LocalDateTime dateTimeTo;
        private ClientData clientData;
        private boolean isWholeDayTask;
        private TaskState state;

        public SimpleTimetableTask(String id, String tag, String description, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, ClientData clientData, boolean isWholeDayTask, TaskState state) {
            this.id = id;
            this.tag = tag;
            this.description = description;
            this.dateTimeFrom = dateTimeFrom;
            this.dateTimeTo = dateTimeTo;
            this.clientData = clientData;
            this.isWholeDayTask = isWholeDayTask;
            this.state = state;
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
        public ClientData getClientData() {
            return clientData;
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
        public boolean isWholeDayTask() {
            return isWholeDayTask;
        }

        @Override
        public TaskState getState() {
            return state;
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
