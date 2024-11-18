package shell;

@FunctionalInterface
public interface Command {
    void execute(String arg);
}
