package shell;

import util.Regex;

import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandExecutor {

    private final Map<Pattern, Supplier<Command>> commands;

    public CommandExecutor() {
        commands = Map.of(
                Regex.EXIT.get(), ExitCommand::new
        );
    }

    public void execute(String command) {
        commands.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                e -> e.getKey().matcher(command),
                                Map.Entry::getValue
                        ))
                .entrySet()
                .stream()
                .filter(e -> e.getKey().find())
                .findFirst()
                .ifPresentOrElse(
                        e -> e.getValue().get().execute(e.getKey().group(1)),
                        () -> System.out.printf("%s: command not found%n", command)
                );
    }

}
