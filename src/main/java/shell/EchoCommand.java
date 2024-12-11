package shell;

import util.Strings;

public class EchoCommand implements Command {

    @Override
    public void execute(String arg) {
        switch (arg) {
            case null -> throw new IllegalArgumentException("Input argument must not be null.");
            case String str when isSingleQuoted(str) -> System.out.println(str.substring(1, str.length() - 1));
            default -> System.out.println(Strings.normalize(arg));
        }
    }

    private boolean isSingleQuoted(String arg) {
        return arg.startsWith("'") && arg.endsWith("'");
    }

}
