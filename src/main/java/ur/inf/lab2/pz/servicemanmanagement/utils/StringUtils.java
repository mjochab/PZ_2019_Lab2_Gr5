package ur.inf.lab2.pz.servicemanmanagement.utils;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static String reverseFormat(String descriptionPattern, String attributeNameLookingFor, String source, char separator) {
        List<String> splittedPatternAttributes = Arrays.asList(descriptionPattern.split(String.valueOf(separator)))
                .stream()
                .map(str -> str.replace("\n", "").trim())
                .collect(Collectors.toList());

        int indexOfAttribute = -1;
        for (String attributeName : splittedPatternAttributes) {

            if (attributeName.equals(attributeNameLookingFor))
                indexOfAttribute = splittedPatternAttributes.indexOf(attributeName);

        }

        if (indexOfAttribute == -1)
            throw new IllegalArgumentException();

        String[] sourceAttributes = source.split(String.valueOf(separator));

        if (sourceAttributes.length - 1 < indexOfAttribute)
            throw new IllegalArgumentException();

        return sourceAttributes[indexOfAttribute]
                .replace("\n", "")
                .trim();
    }
}
