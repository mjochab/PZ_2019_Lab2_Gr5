package ur.inf.lab2.pz.servicemanmanagement.timetable.task;

public class ClientDataImplx implements ClientData {

    private String firstName;
    private String surname;
    private String phoneNumber;
    private String street;
    private String houseNumber;
    private String flatNumber;
    private String city;


    public ClientDataImplx(String firstName, String surname, String phoneNumber, String street, String houseNumber, String flatNumber, String city) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.city = city;
    }

//    public ClientDataImplx(Task task) {
//        this.firstName = task.getClient().getFirstName();
//        this.surname = task.getClient().getLastName();
//        this.phoneNumber = String.valueOf(task.getClient().getPhoneNumber());
//        this.street = task.getClient().getStreet();
//        this.houseNumber = String.valueOf(task.getClient().getHouseNumber());
//        this.flatNumber = String.valueOf(task.getClient().getApartmentNumber());
//        this.city = task.getClient().getCity();
//    }

    @Override
    public String getFirstname() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
