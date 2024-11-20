package shell;

import util.Pair;
import util.Paths;
import util.Regex;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
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
        switch (command) {
            case String c when isBuiltinCommand(c) -> commands.entrySet()
                    .stream()
                    .map(e -> new Pair<>(e.getKey().matcher(command), e.getValue()))
                    .filter(e -> e.first().find())
                    .findFirst()
                    .ifPresentOrElse(
                            e -> e.second().execute(e.first().group(1)),
                            () -> System.out.printf("%s: command not found%n", command)
                    );
            case String c when isExternalProgram(c) -> new ExecProgramCommand().execute(c);
            default -> System.out.printf("%s: command not found%n", command);
        }
    }

    private boolean isBuiltinCommand(String command) {
        Pattern pattern = Regex.BUILTIN.get();
        Matcher matcher = pattern.matcher(command);
        return matcher.find();
    }

    private boolean isExternalProgram(String command) {
        String[] parts = command.split("\\s");
        Optional<Path> application = Paths.resolveApplication(parts[0]);
        return application.isPresent();
    }

}
