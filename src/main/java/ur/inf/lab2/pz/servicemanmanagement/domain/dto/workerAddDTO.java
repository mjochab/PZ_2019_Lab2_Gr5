package ur.inf.lab2.pz.servicemanmanagement.domain.dto;

public class workerAddDTO {
    private String groupName;
    private String email;

    public workerAddDTO(String email, String groupName) {
        this.groupName = groupName;
        this.email = email;
    }

    public String getGroupName() {
        return groupName;
    }
    public String getEmail() {
        return email;
    }

}
