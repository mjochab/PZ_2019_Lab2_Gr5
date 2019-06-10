package ur.inf.lab2.pz.servicemanmanagement.view;

/**
 * Enum wykorzystywany podczas wyświetlania panelu
 */
public enum Layout { // TODO inicial component here

    PANEL("layout/panel-layout", "placeForComponent"),
    START("layout/empty-layout", "placeForComponent");

    private String fxmlPath;
    private String mainComponentId;

    Layout(String fxmlPath, String mainComponentId) {
        this.fxmlPath = fxmlPath;
        this.mainComponentId = mainComponentId;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public String getMainComponentId() {
        return mainComponentId;
    }
}
