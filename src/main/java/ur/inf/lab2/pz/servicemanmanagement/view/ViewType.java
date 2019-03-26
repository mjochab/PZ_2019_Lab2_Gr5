package ur.inf.lab2.pz.servicemanmanagement.view;

public enum ViewType {
    MAIN("main"),
    SECONDARY("secondary"),
    DASHBOARD("Dashboard"),
    MANAGERDATA("manager-data"),
    MANAGER_TIMETABLE("manager-timetable"),
    LOGIN("login"),
    MANAGER_REGISTER("managerRegister"),
    SERVICEMAN_REGISTER("servicemanRegister");

    private String fxmlName;

    ViewType(String fxmlName) {
        this.fxmlName = fxmlName;
    }

    String getFXMLName() { return this.fxmlName; }
}
