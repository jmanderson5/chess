package ui;

import model.UserData;
import server.ServerFacade;

public class PreLogin {
    public boolean run(String input, ServerFacade serverFacade) {
        String[] parts = input.split(" ");
        if (parts[0].equals("register")) { register(parts[1], parts[2], parts[3]); }
        if (parts[0].equals("login")) { login(parts[1], parts[2]); }
        if (parts[0].equals("help")) { help(); }

        return true;
    }

    private void register(String username, String password, String email) {
        UserData user = new UserData(username, password, email);

    }

    private void login(String username, String password) {

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
