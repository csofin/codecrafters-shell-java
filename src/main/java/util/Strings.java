package util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Strings {

    private static final List<String> SPECIAL_CHARACTERS = List.of("\\", "\\n", "\"", "`", "$");

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

    public static boolean isQuoted(String str) {
        return isSingleQuoted(str) || isDoubleQuoted(str);
    }

    public static boolean isStartQuoted(String str) {
        return str.startsWith("'") || str.startsWith("\"");
    }

    public static boolean isSingleQuoted(String str) {
        return str.startsWith("'") && str.endsWith("'");
    }

    public static boolean isDoubleQuoted(String str) {
        return str.startsWith("\"") && str.endsWith("\"");
    }

    public static String parseNoQuoted(String str) {
        Objects.requireNonNull(str);
        Function<String, String> removeExtraSpaces = Strings::removeExtraSpaces;
        Function<String, String> removeBackslashes = Strings::removeBackslashes;
        return removeExtraSpaces.andThen(removeBackslashes).apply(str);
    }

    public static String parseSingleQuoted(String str) {
        Objects.requireNonNull(str);
        return str.replaceAll("'", "");
    }

    public static String parseDoubleQuoted(String str) {
        Objects.requireNonNull(str);
        return str.contains("\\") ? parseWithPreservation(str) : parseWithoutPreservation(str);
    }

    private static String parseWithPreservation(String str) {
        StringBuilder builder = new StringBuilder();

        List<String> tokens = Arrays.stream(str.splitWithDelimiters("\"|\\\\n|\\\\", 0))
                .filter(Predicate.not(String::isBlank))
                .toList();

        BiPredicate<String, Boolean> mustSkip = (token, isAfterBackslash) -> ("\"".equals(token) || "\\".equals(token)) && !isAfterBackslash;

        BiPredicate<String, Boolean> mustPreserveBackslash = (token, isAfterBackslash) -> !SPECIAL_CHARACTERS.contains(token) && isAfterBackslash;

        boolean isAfterBackslash = false;
        for (String token : tokens) {
            if (mustSkip.test(token, isAfterBackslash)) {
                isAfterBackslash = "\\".equals(token);
                continue;
            }
            if (mustPreserveBackslash.test(token, isAfterBackslash)) {
                builder.append("\\");
            }
            builder.append(token);
            isAfterBackslash = false;
        }

        return builder.toString();
    }

    private static String parseWithoutPreservation(String str) {
        Pattern pattern = Regex.DOUBLE_QUOTED_ARGS.get();
        Matcher matcher = pattern.matcher(str);
        return matcher
                .results()
                .map(matchResult -> matchResult.group("arg"))
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.joining(" "));
    }

    private static String removeExtraSpaces(String str) {
        return str.replaceAll("\\s{2,}", " ").strip();
    }

    private static String removeBackslashes(String str) {
        return str.replaceAll("\\\\", "");
    }

}
