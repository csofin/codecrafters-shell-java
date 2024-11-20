package shell;

import util.Paths;

public class TypeCommand implements Command {

    @Override
    public void execute(String arg) {
        switch (arg) {
            case String x when ShellCommand.isValid(x) -> System.out.printf("%s is a shell builtin%n", x);
            case String x when Paths.resolveApplication(x).isPresent() ->
                    System.out.printf("%s is %s%n", x, Paths.resolveApplication(x).get());
            default -> System.out.printf("%s: not found%n", arg);
        }
    }

}
