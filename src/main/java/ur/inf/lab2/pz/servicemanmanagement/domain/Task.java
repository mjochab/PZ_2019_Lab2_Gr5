package ur.inf.lab2.pz.servicemanmanagement.domain;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import org.jetbrains.annotations.NotNull;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.TaskStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Task extends RecursiveTreeObject<Task> implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @JoinColumn(name = "creation_date")
    private Date creationDate;

    @JoinColumn(name = "visit_date")
    private Date visitDate;


    public Task(User author, User teamLeader, String title, String details, Client client, TaskStatus status, Date creationDate, Date visitDate) {
        this.author = author;
        this.teamLeader = teamLeader;
        this.title = title;
        this.details = details;
        this.client = client;
        this.status = status;
        this.creationDate = creationDate;
        this.visitDate = visitDate;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean hasTeamLeader() {
        return this.getTeamLeader() != null;
    }



    @Override
    public int compareTo(Task o) {
        if (getCreationDate() == null || o.getCreationDate() == null) {
            return 0;
        }
        return getCreationDate().compareTo(o.getCreationDate());
    }
}
