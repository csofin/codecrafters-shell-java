package shell;

public class EchoCommand implements Command {

    @Override
    public void execute(String arg) {
        System.out.println(arg);
    }

}
