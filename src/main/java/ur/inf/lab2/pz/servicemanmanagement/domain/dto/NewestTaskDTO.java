package ur.inf.lab2.pz.servicemanmanagement.domain.dto;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.Date;

public class NewestTaskDTO extends RecursiveTreeObject<NewestTaskDTO> {

    private String title;
    private String details;
    private String teamLeader;
    private String clientName;
    private String address;
    private Date creationDate;

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

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public NewestTaskDTO(String title, String details, String teamLeader, String clientName, String address, Date creationDate) {
        this.title = title;
        this.details = details;
        this.teamLeader = teamLeader;
        this.clientName = clientName;
        this.address = address;
        this.creationDate = creationDate;
    }

    public NewestTaskDTO() {
    }
}
