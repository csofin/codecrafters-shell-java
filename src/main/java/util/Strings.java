package util;

import java.util.Objects;

public final class Strings {

    public static String afterLast(String string, String substring) {
        Objects.requireNonNull(string);
        Objects.requireNonNull(substring);

        int index = string.lastIndexOf(substring);

        return index < 0 || index == string.length() - 1 ? "" : string.substring(index + substring.length());
    }

    public static int occurrences(String string, String substring) {
        Objects.requireNonNull(string);
        Objects.requireNonNull(substring);

        int index = 0;
        int count = 0;
        while (index >= 0) {
            index = string.indexOf(substring, index);
            if (index >= 0) {
                index += substring.length();
                count++;
            }
        }

        return count;
    }

}
