package ui;

import java.util.Scanner;

public class UserInterface {

    private boolean loggedIn = false;
    private boolean exit = false;

    public void run() {
        PreLogin preLogin = new PreLogin();
        PostLogin postLogin = new PostLogin();

        while (!exit) {
            if (!loggedIn) {
                System.out.print("[LOGGED OUT] >>> ");
                String input = getInput();
                if (input.equals("quit")) {
                    exit = true;
                } else {
                    loggedIn = preLogin.run(input);
                }
            } else {
                System.out.print("[LOGGED IN] >>> ");
                String input = getInput();
                if (input.equals("quit")) {
                    exit = true;
                } else {
                    loggedIn = postLogin.run(input);
                }
            }
        }
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
