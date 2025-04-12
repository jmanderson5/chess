package ui;

import exception.ResponseException;
import server.ServerFacade;

import java.util.Scanner;

public class InGameUi {

    private boolean inGame = true;
    private String input;
    private ServerFacade serverFacade;
    Integer gameID;
    String playerColor;

    public void run(String input, ServerFacade serverFacade, Integer gameID, String playerColor)
            throws ResponseException {
        this.input = input;
        this.serverFacade = serverFacade;
        this.gameID = gameID;
        this.playerColor = playerColor;

        while (inGame) {
            String[] parts = this.input.split(" ");
            if (parts.length == 1 && parts[0].equals("redraw")) { redraw(); }
            else if (parts.length == 1 && parts[0].equals("leave")) { leave(); }
            else if (parts.length == 4 && parts[0].equals("move")) { move(parts[1], parts[3]); }
            else if (parts.length == 1 && parts[0].equals("resign")) { resign(); }
            else if (parts.length == 1 && parts[0].equals("highlight")) { highlight(); }
            else if (parts.length == 1 && parts[0].equals("help")) { help(); }
            else {
                System.out.print("[IN GAME] >>> ");
            }
            if (inGame) {
                getInput();
            }
        }
    }

    private void redraw() throws ResponseException {
        serverFacade.redraw(gameID, playerColor);
    }

    private void leave() throws ResponseException {
        System.out.print("[LOGGED IN] >>> ");
        serverFacade.leave(gameID);
        inGame = false;
    }

    private void move(String beginning, String end) {
        System.out.print("[IN GAME] >>> ");
    }

    private void resign() {
        System.out.print("[IN GAME] >>> ");
    }

    private void highlight() {
        System.out.print("[IN GAME] >>> ");
    }

    private void help() {
        writeHelpText("redraw", " - chessboard");
        writeHelpText("leave", " - chess game");
        writeHelpText("move #,# to #,#", " - to make move");
        writeHelpText("resign", " - from chess game");
        writeHelpText("highlight", " - legal moves");
        writeHelpText("help", " - with possible commands");
        System.out.println(EscapeSequences.RESET_TEXT_COLOR);
        System.out.print("[IN GAME] >>> ");
    }

    private void writeHelpText(String command, String description) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(command);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
        System.out.println(description);
    }

    private void getInput() {
        Scanner scanner = new Scanner(System.in);
        this.input = scanner.nextLine();
    }
}
