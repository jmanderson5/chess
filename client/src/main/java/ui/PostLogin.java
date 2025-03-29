package ui;

import server.ServerFacade;

public class PostLogin {

    private ServerFacade serverFacade;

    public boolean run(String input, ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
        boolean loggedIn = true;

        String[] parts = input.split(" ");
        if (parts.length == 2 && parts[0].equals("create")) { create(); }
        else if (parts.length == 1 && parts[0].equals("list")) { list(); }
        else if (parts.length == 3 && parts[0].equals("join")) { join(); }
        else if (parts.length == 1 && parts[0].equals("observe")) { observe(); }
        else if (parts.length == 1 && parts[0].equals("logout")) { loggedIn = logout(); }
        else if (parts.length == 1 && parts[0].equals("help")) { help(); }
        else {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print("unsuccessful: try again");
            System.out.println(EscapeSequences.RESET_TEXT_COLOR);
        }

        return loggedIn;
    }

    private void create() {

    }

    private void list() {

    }

    private void join() {

    }

    private void observe() {

    }

    private boolean logout() {
        return true;
    }

    private void help() {
        writeHelpText("create <NAME>", " - a game");
        writeHelpText("list", " - games");
        writeHelpText("join <ID> [WHITE|BLACK]", " - a game");
        writeHelpText("observe", " - a game");
        writeHelpText("logout", " - when you are done");
        writeHelpText("quit", " - playing chess");
        writeHelpText("help", " - with possible commands");
        System.out.println(EscapeSequences.RESET_TEXT_COLOR);
    }

    private void writeHelpText(String command, String description) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(command);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
        System.out.println(description);
    }
}
