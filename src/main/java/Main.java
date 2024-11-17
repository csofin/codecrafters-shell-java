import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("$ ");

        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        System.out.printf("%s: command not found%n", command);
    }

}
