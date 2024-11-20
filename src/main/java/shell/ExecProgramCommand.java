package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class ExecProgramCommand implements Command {

    @Override
    public void execute(String arg) {
        String[] command = arg.split("\\s");
        try {
            Process process = new ProcessBuilder(command).start();
            if (process.waitFor() != 0) {
                System.out.printf("%s: command not found%n", command[0]);
                return;
            }

            try (BufferedReader reader = process.inputReader()) {
                System.out.println(reader.lines().collect(Collectors.joining("\n")));
            }
        } catch (IOException | InterruptedException e) {
            System.out.printf("%s: command not found%n", command[0]);
        }

    }

}
