package ui;

import server.ServerFacade;

import java.util.Scanner;

public class UserInterface {

    private boolean loggedIn = false;
    private boolean exit = false;
    private boolean initialLogin = true;

    public void run(ServerFacade serverFacade, String url) {
        PreLogin preLogin = new PreLogin();
        PostLogin postLogin = new PostLogin();
        System.out.print("[LOGGED OUT] >>> ");
        String input = getInput();

        while (!exit) {
            if (!loggedIn) {
                if (input.equals("quit")) {
                    exit = true;
                } else {
                    loggedIn = preLogin.run(input, serverFacade);
                }
                if (!loggedIn && !exit) {
                    System.out.print("[LOGGED OUT] >>> ");
                    input = getInput();
                }
            } else {
                if (initialLogin) {
                    System.out.print("[LOGGED IN] >>> ");
                    input = getInput();
                    initialLogin = false;
                }

                if (input != null && input.equals("quit")) {
                    exit = true;
                } else {
                    assert input != null;
                    loggedIn = postLogin.run(input, serverFacade, url);
                    input = getInput();
                }

                if (!loggedIn) {
                    initialLogin = true;
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
