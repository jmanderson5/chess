package ui;

import server.ServerFacade;

import java.util.Scanner;

public class UserInterface {

    private boolean loggedIn = false;
    private boolean exit = false;
    private boolean inGame = false;

    public void run(ServerFacade serverFacade) {
        PreLogin preLogin = new PreLogin();
        PostLogin postLogin = new PostLogin();

        while (!exit) {
            if (!loggedIn) {
                System.out.print("[LOGGED OUT] >>> ");
                String input = getInput();
                if (input.equals("quit")) {
                    exit = true;
                } else {
                    loggedIn = preLogin.run(input, serverFacade);
                }
            } else if (!inGame) {
                System.out.print("[LOGGED IN] >>> ");
                String input = getInput();
                if (input.equals("quit")) {
                    exit = true;
                } else {
                    loggedIn = postLogin.run(input, serverFacade);
                }
            }
        }

        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.println("quit");
        System.out.println(EscapeSequences.RESET_TEXT_COLOR);
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
