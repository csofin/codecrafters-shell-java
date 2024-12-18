package util;

import shell.ShellCommand;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Regex {

    public static final Supplier<Pattern> PATH = () -> Pattern.compile("^([a-zA-Z0-9-_:/]+)");

    public static final Supplier<Pattern> EXIT = () -> Pattern.compile("^%s\\s(\\d+)".formatted(ShellCommand.EXIT.getCommand()));

    public static final Supplier<Pattern> ECHO = () -> Pattern.compile("^%s\\s(.+)".formatted(ShellCommand.ECHO.getCommand()));

    public static final Supplier<Pattern> TYPE = () -> Pattern.compile("^%s\\s(\\w+)".formatted(ShellCommand.TYPE.getCommand()));

    public static final Supplier<Pattern> PWD = () -> Pattern.compile("^%s".formatted(ShellCommand.PWD.getCommand()));

    public static final Supplier<Pattern> CD = () -> Pattern.compile("^%s\\s([a-zA-Z0-9-_/.~]+)".formatted(ShellCommand.CD.getCommand()));

    public static final Supplier<Pattern> ABSOLUTE_PATH = () -> Pattern.compile("^[a-zA-Z0-9-_:/]+");

    public static final Supplier<Pattern> RELATIVE_PATH = () -> Pattern.compile("^\\.{1,2}[/a-zA-Z0-9-_*]*");

    public static final Supplier<Pattern> BUILTIN = () -> Pattern.compile("^(%s)".formatted(ShellCommand.builtinCommands()));

    public static final Supplier<Pattern> COMMAND = () -> Pattern.compile("^(?<command>[a-zA-Z_]+)\\s(?<args>.*)");

    public static final Supplier<Pattern> NO_QUOTED_ARGS = () -> Pattern.compile("(?<arg>[^']*)");

    public static final Supplier<Pattern> SINGLE_QUOTED_ARGS = () -> Pattern.compile("('(?<arg>[^']*)')");

    public static final Supplier<Pattern> DOUBLE_QUOTED_ARGS = () -> Pattern.compile("\"(?<arg>.*?)\"");

}
