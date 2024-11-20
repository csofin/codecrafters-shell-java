package shell;

import util.Paths;

public class WorkingDirectoryCommand implements Command {

    @Override
    public void execute(String arg) {
        System.out.println(Paths.pwd());
    }

}
