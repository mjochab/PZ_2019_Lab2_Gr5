package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

public enum TaskState {
    TODO("Do zrobienia"), DONE("Gotowe");

    private String name;

    TaskState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
