package shell;

public class TypeCommand implements Command {

    @Override
    public void execute(String arg) {
        if (ShellCommand.isValid(arg)) {
            System.out.printf("%s is a shell builtin%n", arg);
        } else {
            System.out.printf("%s: not found%n", arg);
        }
    }

}
