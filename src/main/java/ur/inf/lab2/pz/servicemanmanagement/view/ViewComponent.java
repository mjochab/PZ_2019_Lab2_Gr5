package ur.inf.lab2.pz.servicemanmanagement.view;

public enum ViewComponent {
    LOGIN("component/login"),
    DASHBOARD("component/manager/dashboard"),
    LOADING("component/loading"),
    TIMETABLE("component/timetable"),
    EMPLOYEES("component/manager/employees"),
    MANAGER_DATA("component/manager/user-data"),
    MANAGER_REGISTER("component/manager/register"),
    SERVICEMAN_DATA("component/serviceman/user-data"),
    SERVICEMAN_REGISTER("component/serviceman/serviceman-first-login"),
    NEW_TASK_DIALOG("component/manager/new-task-dialog"),
    NEW_CLIENT_DIALOG("component/manager/new-client"),
    TIMETABLE_GROUP_NOT_SELECTED("/component/manager/group-not-selected"),
    EDIT_TASK_DIALOG("/component/edit-task-dialog"),
    SERVICEMAN_TIMETABLE("/component/serviceman/serviceman-timetable");


    private String fxmlPath;
    private ViewComponent(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
