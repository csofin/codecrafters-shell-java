package shell;

import util.Pair;
import util.Regex;

import java.util.Map;
import java.util.regex.Pattern;

public class CommandExecutor {

    private final Map<Pattern, Command> commands;

    public CommandExecutor() {
        commands = Map.of(
                Regex.EXIT.get(), new ExitCommand(),
                Regex.ECHO.get(), new EchoCommand(),
                Regex.TYPE.get(), new TypeCommand()
        );
    }

    public void execute(String command) {
        commands.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey().matcher(command), e.getValue()))
                .filter(e -> e.first().find())
                .findFirst()
                .ifPresentOrElse(
                        e -> e.second().execute(e.first().group(1)),
                        () -> System.out.printf("%s: command not found%n", command)
                );
    }

}
