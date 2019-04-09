package ur.inf.lab2.pz.servicemanmanagement.domain;

public class SecurityContext {

    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User u) {
        loggedUser = u;
    }
}


