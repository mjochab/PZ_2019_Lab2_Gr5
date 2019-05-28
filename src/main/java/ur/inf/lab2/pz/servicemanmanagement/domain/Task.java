package ur.inf.lab2.pz.servicemanmanagement.domain;

import ur.inf.lab2.pz.servicemanmanagement.timetable.task.TaskState;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Encja reprezentująca zadanie które menadżer przyedziela je swoim serwisantom
 */

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tag;
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Serviceman leader;

    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;

    @Enumerated(EnumType.STRING)
    private TaskState state;

    private boolean wholeDayTask;

    public Task(String tag, String description, Client client) {
        this.tag = tag;
        this.description = description;
        this.client = client;
    }

    public Task() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Serviceman getLeader() {
        return leader;
    }

    public void setLeader(Serviceman leader) {
        this.leader = leader;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public TaskState getState() {
        return state;
    }

    public boolean isWholeDayTask() {
        return wholeDayTask;
    }

    public void setWholeDayTask(boolean wholeDayTask) {
        this.wholeDayTask = wholeDayTask;
    }
}
