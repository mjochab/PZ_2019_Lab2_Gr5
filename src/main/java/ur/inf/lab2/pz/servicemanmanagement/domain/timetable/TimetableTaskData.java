package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import java.time.LocalDateTime;

public class TimetableTaskData implements AllocatedTask {

    private String id;
    private String tag;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public TimetableTaskData(String id, String tag, String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.tag = tag;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
        return startDateTime;
    }

    @Override
    public LocalDateTime getDateTimeTo() {
        return endDateTime;
    }
}
