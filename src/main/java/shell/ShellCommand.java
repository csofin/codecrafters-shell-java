package shell;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ShellCommand {
    EXIT("exit"),
    ECHO("echo"),
    TYPE("type"),
    PWD("pwd"),
    CD("cd");

    private final String command;

    ShellCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static boolean isValid(String command) {
        return Arrays.stream(ShellCommand.values())
                .anyMatch(c -> c.getCommand().equals(command));
    }

    public static String builtinCommands() {
        return Arrays.stream(ShellCommand.values())
                .map(ShellCommand::getCommand)
                .collect(Collectors.joining("|"));
    }

}
