package ur.inf.lab2.pz.servicemanmanagement.domain;

public class SecurityContext {

    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User u) {
        loggedUser = u;
    }

    public static boolean isLoggedManager() {
        return loggedUserIs("ROLE_MANAGER");
    }

    public static boolean isLoggedServiceman() {
        return loggedUserIs("ROLE_SERVICEMAN");
    }

    private static boolean loggedUserIs(String role) {
        if (loggedUser == null)
            return false;
        else
            return loggedUser.getRole().getRole().equals(role);
    }
}


