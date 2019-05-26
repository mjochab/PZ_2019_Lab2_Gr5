package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class UnallocatedTaskTableItem extends RecursiveTreeObject<UnallocatedTaskTableItem> implements UnallocatedTask {

    private String id;
    private String tag;
    private String description;
    private ClientData clientData;

    public UnallocatedTaskTableItem(UnallocatedTask task) {
        this.id = task.getId();
        this.tag = task.getTag();
        this.description = task.getDescription();
        this.clientData = task.getClientData();
    }

    public UnallocatedTaskTableItem(String id, String tag, String description, ClientData clientData) {
        this.id = id;
        this.tag = tag;
        this.description = description;
        this.clientData = clientData;
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
    public String toString() {
        return "UnallocatedTaskTableItem{" +
                "id='" + id + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
