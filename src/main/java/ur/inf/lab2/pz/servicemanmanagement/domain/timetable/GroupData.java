package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

public class GroupData {
    private Long leaderId;
    private String groupName;

    public GroupData(Long leaderId, String groupName) {
        this.leaderId = leaderId;
        this.groupName = groupName;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return groupName;
    }
}
