package shell;

import java.util.Arrays;

public enum ShellCommand {
    EXIT("exit"),
    ECHO("echo"),
    TYPE("type");

    private final String command;

    ShellCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static boolean isValid(String command) {
        return Arrays.stream(ShellCommand.values()).anyMatch(c -> c.getCommand().equals(command));
    }

}
