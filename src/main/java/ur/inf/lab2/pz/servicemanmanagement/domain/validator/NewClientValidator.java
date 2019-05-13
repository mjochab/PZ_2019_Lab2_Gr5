package ur.inf.lab2.pz.servicemanmanagement.domain.validator;

import javafx.scene.text.Text;
import org.thymeleaf.util.Validate;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;

public class NewClientValidator {
    String firstNameTextField;
    String lastNameTextField;
    String streetTextField;
    String houseNumberTextField;
    String apartmentNumberTextField;
    String cityTextField;
    String phoneTextField;
    Text firstNameAlert;
    Text lastNameAlert;
    Text streetNumberAlert;
    Text houseNumberAlert;
    Text apartmentNumberAlert;
    Text cityAlert;
    Text phoneAlert;
    Text jpAlert;

    private FXFormValidator validator;

    public NewClientValidator(String firstNameTextField, String lastNameTextField, String streetTextField,
                              String houseNumberTextField, String apartmentNumberTextField, String cityTextField,
                              String phoneTextField ,Text firstNameAlert, Text lastNameAlert, Text streetNumberAlert,
                              Text houseNumberAlert, Text apartmentNumberAlert, Text  cityAlert, Text phoneAlert) {

        this.firstNameTextField = firstNameTextField;
        this.lastNameTextField = lastNameTextField;
        this.streetTextField = streetTextField;
        this.houseNumberTextField = houseNumberTextField;
        this.apartmentNumberTextField = apartmentNumberTextField;
        this.cityTextField = cityTextField;
        this.phoneTextField = phoneTextField;
        this.lastNameAlert = lastNameAlert;
        this.firstNameAlert = firstNameAlert;
        this.streetNumberAlert = streetNumberAlert;
        this.houseNumberAlert = houseNumberAlert;
        this.apartmentNumberAlert = apartmentNumberAlert;
        this.cityAlert = cityAlert;
        this.phoneAlert = phoneAlert;
    }

    public void validate() {

        clearAlerts();
        validator = new FXFormValidator();
        validator.validateEmptyOrWhitespaceOnly(firstNameTextField, firstNameAlert);
        validator.validateEmptyOrWhitespaceOnly(lastNameTextField, lastNameAlert);
        validator.validateEmptyOrWhitespaceOnly(streetTextField, streetNumberAlert);
        validator.validateEmptyOrWhitespaceOnly(houseNumberTextField, houseNumberAlert);
        validator.validateEmptyOrWhitespaceOnly(apartmentNumberTextField, apartmentNumberAlert);
        validator.validateEmptyOrWhitespaceOnly(cityTextField, cityAlert);
        validator.validateEmptyOrWhitespaceOnly(phoneTextField, phoneAlert);
        validator.isNumber(houseNumberTextField, houseNumberAlert);
        validator.isNumber(apartmentNumberTextField, apartmentNumberAlert);
        validator.isNumber(phoneTextField, phoneAlert);
    }

    private void clearAlerts() {

        firstNameAlert.setVisible(false);
        lastNameAlert.setVisible(false);
        streetNumberAlert.setVisible(false);
        houseNumberAlert.setVisible(false);
        apartmentNumberAlert.setVisible(false);
        cityAlert.setVisible(false);
        phoneAlert.setVisible(false);
    }

    public String getFirstNameTextField() {
        return firstNameTextField;
    }
    public String getLastNameTextField() {
        return lastNameTextField;
    }
    public String getStreetTextField() {
        return streetTextField;
    }
    public String getHouseNumberTextField() {
        return houseNumberTextField;
    }
    public String getApartmentNumberTextField() {
        return apartmentNumberTextField;
    }
    public String getCityTextField() {
        return cityTextField;
    }
    public String getPhoneTextField() {
        return phoneTextField;
    }

    public Text getFirstNameAlert() { return  firstNameAlert; }
    public Text getLastNameAlert() { return  lastNameAlert; }
    public Text getStreetNumberAlert() { return  streetNumberAlert; }
    public Text getHouseNumberAlert() { return  houseNumberAlert; }
    public Text getApartmentNumberAlert() { return  apartmentNumberAlert; }
    public Text getCityAlert() { return  cityAlert; }
    public Text getPhoneAlert() { return  phoneAlert; }

    public FXFormValidator getValidator() {
        return validator;
    }
}