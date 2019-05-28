package ur.inf.lab2.pz.servicemanmanagement.domain.dto;

import java.time.LocalDateTime;

/**
 * Klasa data transfer object używana do przesyłania statusu wykonywanego zadania używana w ManagerDashboardController
 */
public class TaskStatusDTO {


    private String Tag;
    private String desciption;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }
}
