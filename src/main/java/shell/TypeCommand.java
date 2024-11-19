package shell;

import config.Environment;
import util.Paths;

public class TypeCommand implements Command {

    @Override
    public void execute(String arg) {
        if (ShellCommand.isValid(arg)) {
            System.out.printf("%s is a shell builtin%n", arg);
        } else {
            Environment.getInstance().getPaths()
                    .stream()
                    .filter(p -> Paths.exists(p, arg))
                    .findFirst()
                    .ifPresentOrElse(
                            p -> System.out.printf("%s is %s%n", arg, p.resolve(arg)),
                            () -> System.out.printf("%s: not found%n", arg));
        }
    }

}
