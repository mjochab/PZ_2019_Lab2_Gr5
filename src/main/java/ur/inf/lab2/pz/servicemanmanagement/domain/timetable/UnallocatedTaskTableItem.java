package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDateTime;

public class UnallocatedTaskTableItem extends RecursiveTreeObject<UnallocatedTaskTableItem> implements UnallocatedTask {

    private String id;
    private String tag;
    private String description;

    public UnallocatedTaskTableItem(UnallocatedTask task) {
        this.id = task.getId();
        this.tag = task.getTag();
        this.description = task.getDescription();
    }

    public UnallocatedTaskTableItem(String id, String tag, String description) {
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
    public String toString() {
        return "UnallocatedTaskTableItem{" +
                "id='" + id + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
