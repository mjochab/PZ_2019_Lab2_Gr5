package ur.inf.lab2.pz.servicemanmanagement.service;

import org.springframework.stereotype.Component;

public class MockSecurityContext {
    public enum UserType { MANAGER, SERVICEMAN }

    public static UserType loggedUser = null;


}
