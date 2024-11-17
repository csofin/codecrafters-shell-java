import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        prompt();
        while (in.hasNextLine()) {
            String command = in.nextLine();
            System.out.printf("%s: command not found%n", command);
            prompt();
        }
    }

    private static void prompt() {
        System.out.print("$ ");
    }

}
