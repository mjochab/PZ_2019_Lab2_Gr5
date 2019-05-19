package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;

import java.time.LocalDateTime;
import java.util.concurrent.RecursiveTask;

public class UnallocatedTask extends RecursiveTreeObject<UnallocatedTask> implements TimetableTask {

    private String id;
    private String tag;
    private String description;

    public UnallocatedTask(TimetableTask task) {
        this.id = task.getId();
        this.tag = task.getTag();
        this.description = task.getDescription();
    }

    public UnallocatedTask(String id, String tag, String description) {
        this.id = id;
        this.tag = tag;
        this.description = description;
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
        return null;
    }

    @Override
    public LocalDateTime getDateTimeTo() {
        return null;
    }

    @Override
    public String toString() {
        return "UnallocatedTask{" +
                "id='" + id + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
