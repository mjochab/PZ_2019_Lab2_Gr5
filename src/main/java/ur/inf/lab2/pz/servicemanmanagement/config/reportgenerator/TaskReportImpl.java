package ur.inf.lab2.pz.servicemanmanagement.config.reportgenerator;

public class TaskReportImpl implements TaskReport {

    private String teamLeader;
    private String title;
    private String details;
    private String clientFirstName;
    private String clientLastName;
    private String clientPhoneNumber;
    private String clientStreet;
    private String clientHouseNumber;
    private String clientApartmentNumber;
    private String clientCity;

    public TaskReportImpl(String teamLeader, String title, String details, String clientFirstName, String clientLastName, String clientPhoneNumber,
                          String clientStreet, String clientHouseNumber, String clientApartmentNumber, String clientCity) {
        this.teamLeader = teamLeader;
        this.title = title;
        this.details = details;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientStreet = clientStreet;
        this.clientCity = clientCity;
        this.clientHouseNumber = clientHouseNumber;
        this.clientApartmentNumber = clientApartmentNumber;
    }


    @Override
    public String getTeamLeader() {
        return this.teamLeader;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDetails() {
        return this.details;
    }

    @Override
    public String getClientFirstName() {
        return this.clientFirstName;
    }

    @Override
    public String getClientLastName() {
        return this.clientLastName;
    }

    @Override
    public String getClientPhoneNumber() {
        return this.clientPhoneNumber;
    }

    @Override
    public String getClientStreet() {
        return this.clientStreet;
    }

    @Override
    public String getClientHouseNumber() {
        return this.clientHouseNumber;
    }

    @Override
    public String getClientApartmentNumber() {
        return this.clientApartmentNumber;
    }

    @Override
    public String getClientCity() {
        return this.clientCity;
    }
}
