package shell;

import util.Files;

public class ConcatenateCommand implements Command {

    @Override
    public void execute(String arg) {
        Files.contents(arg).ifPresent(System.out::print);
    }

}
