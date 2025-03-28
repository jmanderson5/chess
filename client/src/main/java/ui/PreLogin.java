package ui;

public class PreLogin {
    public boolean run(String input) {
        if (input.equals("help")) { help(); }

        return false;
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
