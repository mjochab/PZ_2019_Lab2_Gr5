package ur.inf.lab2.pz.servicemanmanagement.utils;


public class StringUtils {

    public static boolean isEmptyOrWhitespaceOnly(String str) {

        if (str == null || str.length() == 0) {

            return true;

        }

        int length = str.length();

        for (int i = 0; i < length; i++) {

            if (Character.isWhitespace(str.charAt(i))) {

                return true;

            }

        }
        return false;
    }

    public static boolean isEmpty(String str) {

        if (str == null || str.length() == 0) {

            return true;
        }
        return false;
    }
}
