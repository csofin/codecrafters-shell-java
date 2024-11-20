import shell.CommandExecutor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CommandExecutor executor = new CommandExecutor();

        Scanner in = new Scanner(System.in);

        prompt();
        while (in.hasNextLine()) {
            String command = in.nextLine().strip();
            executor.execute(command);
            prompt();
        }
    }

    private static void prompt() {
        System.out.print("$ ");
    }

}
