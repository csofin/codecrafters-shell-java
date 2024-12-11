package shell;

import util.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExecProgramCommand implements Command {

    @Override
    public void execute(String arg) {
        String command = null;
        String args = null;
        Matcher matcher = Regex.COMMAND.get().matcher(arg);
        if (matcher.find()) {
            command = matcher.group("command");
            args = matcher.group("args");
        }

        if (Objects.isNull(command)) {
            return;
        }

        final List<String> processCommands = new ArrayList<>();

        processCommands.add(command);

        Optional.ofNullable(args)
                .ifPresent(arguments ->
                        resolveRegexPattern(arguments)
                                .matcher(arguments)
                                .results()
                                .map(matchResult -> matchResult.group("arg"))
                                .map(String::strip)
                                .filter(Predicate.not(String::isBlank))
                                .forEach(processCommands::add)
                );

        try {
            Process process = new ProcessBuilder(processCommands).start();
            if (process.waitFor() != 0) {
                System.out.printf("%s: command not found%n", command);
                return;
            }

            try (BufferedReader reader = process.inputReader()) {
                System.out.println(reader.lines().collect(Collectors.joining("\n")));
            }
        } catch (IOException | InterruptedException e) {
            System.out.printf("%s: command not found%n", command);
        }

    }

    private Pattern resolveRegexPattern(String arg) {
        return switch (arg) {
            case String str when str.startsWith("\"") -> Regex.DOUBLE_QUOTED_ARGS.get();
            case String str when str.startsWith("'") -> Regex.SINGLE_QUOTED_ARGS.get();
            default -> Regex.NO_QUOTED_ARGS.get();
        };
    }

}
