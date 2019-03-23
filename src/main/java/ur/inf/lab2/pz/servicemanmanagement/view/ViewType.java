package ur.inf.lab2.pz.servicemanmanagement.view;

public enum ViewType {
    MAIN("main"),
    SECONDARY("secondary"),
    LOGIN("login"),
    REGISTER("register");

    private String fxmlName;

    ViewType(String fxmlName) {
        this.fxmlName = fxmlName;
    }

    String getFXMLName() { return this.fxmlName; }
}
