package ui;

import server.ServerFacade;

import java.util.Scanner;

public class InGameUi {

    private boolean inGame = true;
    private String input;
    private ServerFacade serverFacade;

    public void run(String input, ServerFacade serverFacade) {
        this.input = input;
        this.serverFacade = serverFacade;

        while (inGame) {
            String[] parts = input.split(" ");
            if (parts.length == 1 && parts[0].equals("redraw")) { redraw(); }
            else if (parts.length == 1 && parts[0].equals("leave")) { leave(); }
        }
    }

    private void redraw() {
        getInput();
    }

    private void leave() {
    }

    private void getInput() {
        Scanner scanner = new Scanner(System.in);
        this.input = scanner.nextLine();
    }
}
