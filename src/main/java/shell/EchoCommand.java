package shell;

import util.Regex;
import util.Strings;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EchoCommand implements Command {

    @Override
    public void execute(String arg) {
        switch (arg) {
            case null -> throw new IllegalArgumentException("Argument must not be null.");
            case String str when isSingleQuoted(str) -> System.out.println(str.replaceAll("'", ""));
            case String str when isDoubleQuoted(str) -> {
                String output = Regex.DOUBLE_QUOTED_ARGS
                        .get()
                        .matcher(str)
                        .results()
                        .map(matchResult -> matchResult.group("arg"))
                        .filter(Predicate.not(String::isBlank))
                        .collect(Collectors.joining(" "));
                System.out.println(output);
            }
            default -> System.out.println(Strings.normalize(arg));
        }
    }

    private boolean isSingleQuoted(String arg) {
        return arg.startsWith("'") && arg.endsWith("'");
    }

    private boolean isDoubleQuoted(String arg) {
        return arg.startsWith("\"") && arg.endsWith("\"");
    }

}
