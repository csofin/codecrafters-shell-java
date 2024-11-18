package util;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Regex {

    public static final Supplier<Pattern> EXIT = () -> Pattern.compile("^exit\\s(\\d+)");

    public static final Supplier<Pattern> ECHO = () -> Pattern.compile("^echo\\s(.+)");

}
