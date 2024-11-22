package shell;

import util.Paths;

public class ChangeDirectoryCommand implements Command {

    @Override
    public void execute(String arg) {
        if (!Paths.changeDirectory(arg)) {
            System.out.printf("cd: %s: No such file or directory%n", arg);
        }
    }

}
