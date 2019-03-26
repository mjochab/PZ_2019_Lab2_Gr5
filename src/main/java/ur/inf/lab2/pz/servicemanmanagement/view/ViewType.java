package ur.inf.lab2.pz.servicemanmanagement.view;

public enum ViewType {
    MAIN("main"),
    SECONDARY("secondary"),
    DASHBOARD("Dashboard"),
    MANAGER_DATA("manager-data"),
    MANAGER_TIMETABLE("manager-timetable"),
    LOGIN("login"),
    MANAGER_REGISTER("managerRegister"),
    SERVICEMAN_REGISTER("servicemanRegister"),
    WORKER_LIST("workerList"),
    SERVICEMAN_TIMETABLE("serviceman-timetable"),
    SERVICEMAN_DATA("serviceman-data");

    private String fxmlName;

    ViewType(String fxmlName) {
        this.fxmlName = fxmlName;
    }

    String getFXMLName() { return this.fxmlName; }
}
