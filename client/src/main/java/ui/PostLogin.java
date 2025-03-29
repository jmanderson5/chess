package ui;

import exception.ResponseException;
import model.GameData;
import model.handler.GameDataShort;
import model.handler.GameResult;
import model.handler.Games;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.List;

public class PostLogin {

    private ServerFacade serverFacade;

    public boolean run(String input, ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
        boolean loggedIn = true;

        String[] parts = input.split(" ");
        if (parts.length == 2 && parts[0].equals("create")) { create(parts[1]); }
        else if (parts.length == 1 && parts[0].equals("list")) { list(); }
        else if (parts.length == 3 && parts[0].equals("join")) { join(parts[1], parts[2]); }
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

    private void create(String game) {
        boolean created = false;
        GameResult gameResult = null;

        try {
            gameResult = serverFacade.createGame(game);
            created = true;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        if (created) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Created '" + game + "' gameID: " + gameResult.gameID());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void list() {
        boolean listed = false;
        Games gamesRecord = null;

        try {
            gamesRecord = serverFacade.listGames();
            listed = true;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        if (listed) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            List<GameDataShort> games = gamesRecord.games();

            System.out.println("Games list:");
            for (GameDataShort game : games) {
                System.out.println(" - " + game.gameName() + " ID:" + game.gameID() + " Black user:" +
                        game.blackUsername() + " White user:" + game.whiteUsername());
            }
            System.out.println(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void join(String gameID, String playerColor) {

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
