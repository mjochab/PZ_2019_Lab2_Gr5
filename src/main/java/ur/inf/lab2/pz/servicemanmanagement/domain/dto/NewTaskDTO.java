package ur.inf.lab2.pz.servicemanmanagement.domain.dto;

import ur.inf.lab2.pz.servicemanmanagement.domain.Client;

public class NewTaskDTO {
    private String title;
    private String details;
    private Client client;

    public NewTaskDTO(String title, String details, Client client) {
        this.title = title;
        this.details = details;
        this.client = client;
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
}
