package shell;

import util.Paths;

public class ChangeDirectory implements Command {

    @Override
    public void execute(String arg) {
        if (!Paths.cd(arg)) {
            System.out.printf("cd: %s: No such file or directory%n", arg);
        }
    }

}
