package util;

import shell.ShellCommand;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Regex {

    public static final Supplier<Pattern> EXIT = () -> Pattern.compile("^%s\\s(\\d+)".formatted(ShellCommand.EXIT.getCommand()));

    public static final Supplier<Pattern> ECHO = () -> Pattern.compile("^%s\\s(.+)".formatted(ShellCommand.ECHO.getCommand()));

    public static final Supplier<Pattern> TYPE = () -> Pattern.compile("^%s\\s(\\w+)".formatted(ShellCommand.TYPE.getCommand()));

}
