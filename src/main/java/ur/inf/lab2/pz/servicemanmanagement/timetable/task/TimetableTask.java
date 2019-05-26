package ur.inf.lab2.pz.servicemanmanagement.timetable.task;

public interface TimetableTask {
    String getId(); // np. M1  (Pierwsza litera taga + numer, następny task z tagiem montaż byłby M2)
    String getTag(); //np. Montaż
    String getDescription();

    ClientData getClientData();
}
