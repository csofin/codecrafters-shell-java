package shell;

import util.Paths;
import util.Regex;
import util.Strings;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandExecutor {

    record CommandInfo(Matcher matcher, Command command) {
    }

    private final Map<Pattern, Command> commands;

    public CommandExecutor() {
        commands = Map.of(
                Regex.EXIT.get(), new ExitCommand(),
                Regex.ECHO.get(), new EchoCommand(),
                Regex.TYPE.get(), new TypeCommand(),
                Regex.PWD.get(), new WorkingDirectoryCommand(),
                Regex.CD.get(), new ChangeDirectoryCommand()
        );
    }

    public void execute(String command) {
        switch (command) {
            case String cmd when isBuiltin(cmd) -> commands.entrySet()
                    .stream()
                    .map(e -> new CommandInfo(e.getKey().matcher(command), e.getValue()))
                    .filter(c -> c.matcher.find())
                    .findFirst()
                    .ifPresentOrElse(
                            c -> c.command.execute(c.matcher.group(Math.min(c.matcher.groupCount(), 1))),
                            () -> System.out.printf("%s: command not found%n", command)
                    );
            case String cmd when isExternalProgram(cmd) -> new ExecProgramCommand().execute(cmd);
            case String cmd when isExecutable(cmd) ->
                    new ConcatenateCommand().execute(cmd.substring(cmd.lastIndexOf(' ') + 1));
            default -> System.out.printf("%s: command not found%n", command);
        }
    }

    private boolean isBuiltin(String command) {
        Pattern pattern = Regex.BUILTIN.get();
        Matcher matcher = pattern.matcher(command);
        return matcher.find();
    }

    private boolean isExternalProgram(String command) {
        String[] parts = command.split("\\s");
        Optional<Path> application = Paths.resolveApplication(parts[0]);
        return application.isPresent();
    }

    private boolean isExecutable(String command) {
        if (!Strings.isStartQuoted(command)) {
            return false;
        }
        return Strings.isQuoted(command.substring(0, command.lastIndexOf(' ')));
    }

}
