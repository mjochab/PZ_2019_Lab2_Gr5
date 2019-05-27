package ur.inf.lab2.pz.servicemanmanagement.domain;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.TaskStatus;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.*;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Task extends RecursiveTreeObject<Task> implements AllocatedTask, UnallocatedTask {

    @Id
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_leader_id")
    private User teamLeader;

    private String title;

    private String details;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @JoinColumn(name = "task_status")
    private TaskStatus status;

    private TaskState state = TaskState.TODO;

    @JoinColumn(name = "creation_date")
    private Date creationDate;

    @JoinColumn(name = "visit_date")
    private Date visitDate;

    private String tag;

    private LocalDateTime dateTimeFrom;

    private LocalDateTime dateTimeTo;

    @Column(name="whole_day")
    private boolean isWholeDayTask;

    private boolean allocated;

    @Transient
    private ClientData clientData;

    @PostConstruct
    public void initClientData() {
        clientData = new ClientDataImpl(this);
    }

    public Task(String id, User author, User teamLeader, String title, String tag, String details,
                Client client, TaskStatus status, Date creationDate, Date visitDate) {
        this.id = id;
        this.author = author;
        this.teamLeader = teamLeader;
        this.title = title;
        this.tag = tag;
        this.details = details;
        this.client = client;
        this.status = status;
        this.creationDate = creationDate;
        this.visitDate = visitDate;
    }

    public Task() {
    }

    public Task(AllocatedTask allocatedTask) {
        this.id = allocatedTask.getId();
        this.state = allocatedTask.getState();
        this.dateTimeFrom = allocatedTask.getDateTimeFrom();
        this.dateTimeTo = allocatedTask.getDateTimeTo();
        this.isWholeDayTask= allocatedTask.isWholeDayTask();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(User teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public ClientData getClientData() {
        return clientData;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public boolean isWholeDayTask() {
        return isWholeDayTask;
    }

    public void setWholeDayTask(boolean wholeDayTask) {
        this.isWholeDayTask = wholeDayTask;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}
