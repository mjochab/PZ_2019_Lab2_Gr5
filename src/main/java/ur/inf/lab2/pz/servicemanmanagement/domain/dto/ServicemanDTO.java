package ur.inf.lab2.pz.servicemanmanagement.domain.dto;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ServicemanDTO extends RecursiveTreeObject<ServicemanDTO> {

    private String email;
    private boolean enabled;
    private String groupName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
