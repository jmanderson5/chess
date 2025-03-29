package ui;

import exception.ResponseException;
import model.UserData;
import model.handler.LoginData;
import server.ServerFacade;

public class PreLogin {

    private ServerFacade serverFacade;

    public boolean run(String input, ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
        boolean loggedIn = false;

        String[] parts = input.split(" ");
        if (parts.length == 4 && parts[0].equals("register")) { loggedIn = register(parts[1], parts[2], parts[3]); }
        else if (parts.length == 3 && parts[0].equals("login")) { loggedIn = login(parts[1], parts[2]); }
        else if (parts.length == 1 && parts[0].equals("help")) { help(); }
        else {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print("unsuccessful: try again");
            System.out.println(EscapeSequences.RESET_TEXT_COLOR);
        }

        return loggedIn;
    }

    private boolean register(String username, String password, String email) {
        UserData user = new UserData(username, password, email);
        boolean registered = false;

        try {
            serverFacade.register(user);
            registered = true;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        if (registered) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print("Successfully registered");
            System.out.println(EscapeSequences.RESET_TEXT_COLOR);
            return registered;
        }
        return registered;
    }

    private boolean login(String username, String password) {
        LoginData loginData = new LoginData(username, password);
        boolean loggedIn = false;

        try {
            serverFacade.login(loginData);
            loggedIn = true;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        if (loggedIn) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print("Successful login");
            System.out.println(EscapeSequences.RESET_TEXT_COLOR);
            return loggedIn;
        }
        return loggedIn;
    }

    private void help() {
        writeHelpText("register <USERNAME> <PASSWORD> <EMAIL>", " - to create an account");
        writeHelpText("login <USERNAME> <PASSWORD>", " - to play chess");
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
