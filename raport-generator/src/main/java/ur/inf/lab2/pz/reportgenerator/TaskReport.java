package ur.inf.lab2.pz.reportgenerator;

import net.sf.jasperreports.engine.JRException;

public interface TaskReport {

    String getTeamLeader();
    String getTitle();
    String getDetails();
    String getClientFirstName();
    String getClientLastName();
    String getClientPhoneNumber();
    String getClientStreet();
    String getClientHouseNumber();
    String getClientApartmentNumber();
    String getClientCity();

}
