package shell;

public class ExitCommand implements Command {

    @Override
    public void execute(String arg) {
        System.exit(Integer.parseInt(arg));
    }

}
