package ur.inf.lab2.pz.servicemanmanagement.timetable.impl;

import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.ClientData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.TaskState;

import java.time.LocalDateTime;

public class TimetableTaskData implements AllocatedTask {

    private String id;
    private String tag;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean isWholeDay;
    private ClientData clientData;
    private TaskState state;

    public TimetableTaskData(String id, String tag, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean isWholeDay, ClientData clientData, TaskState state) {
        this.id = id;
        this.tag = tag;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isWholeDay = isWholeDay;
        this.clientData = clientData;
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
        return startDateTime;
    }

    @Override
    public LocalDateTime getDateTimeTo() {
        return endDateTime;
    }

    @Override
    public boolean isWholeDayTask() {
        return isWholeDay;
    }

    @Override
    public TaskState getState() {
        return state;
    }
}
