package shell;

import util.Strings;

public class EchoCommand implements Command {

    @Override
    public void execute(String arg) {
        String output = switch (arg) {
            case String str when Strings.isDoubleQuoted(str) -> Strings.parseDoubleQuoted(str);
            case String str when Strings.isSingleQuoted(str) -> Strings.parseSingleQuoted(str);
            default -> Strings.parseNoQuoted(arg);
        };
        System.out.println(output);
    }

}
