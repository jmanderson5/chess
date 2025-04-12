package ui;

import exception.ResponseException;
import model.handler.GameDataShort;
import model.handler.Games;
import server.ServerFacade;

import java.util.List;
import java.util.Scanner;

public class PostLogin {

    private ServerFacade serverFacade;

    public boolean run(String input, ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
        boolean loggedIn = true;

        String[] parts = input.split(" ");
        if (parts.length == 2 && parts[0].equals("create")) { create(parts[1]); }
        else if (parts.length == 1 && parts[0].equals("list")) { list(); }
        else if (parts.length == 3 && parts[0].equals("join")) { join(parts[1], parts[2]); }
        else if (parts.length == 2 && parts[0].equals("observe")) { observe(parts[1]); }
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
        Games gamesRecord = null;

        try {
            serverFacade.createGame(game);
            created = true;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        // get game number
        try {
            gamesRecord = serverFacade.listGames();
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        assert gamesRecord != null;
        List<GameDataShort> games = gamesRecord.games();
        int gameNumber = 0;
        int count = 1;
        for (GameDataShort gameData : games) {
            if (gameData.gameName().equals(game)) {
                gameNumber = count;
            }
            count ++;
        }

        if (created) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Created " + gameNumber + ") '" + game + "'");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print("[LOGGED IN] >>> ");
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
            Integer gameNumber = 1;
            for (GameDataShort game : games) {
                System.out.println(gameNumber + ") " + game.gameName() + " Black user:" +
                        game.blackUsername() + " White user:" + game.whiteUsername());
                gameNumber ++;
            }
            System.out.println(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print("[LOGGED IN] >>> ");
        }
    }

    private void join(String gameID, String playerColor) {
        Integer game;
        try {
            game = Integer.parseInt(gameID);
        } catch (NumberFormatException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: User error. Input gameID as int.");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);

            return;
        }

        try {
            Games gamesRecord = serverFacade.listGames();
            List<GameDataShort> games = gamesRecord.games();

            Integer gameNumber = 1;
            for (GameDataShort gameData : games) {
                if (gameNumber.equals(game)) {
                    serverFacade.joinGame(playerColor, gameData.gameID());
                    // enter InGameUi
                    Scanner scanner = new Scanner(System.in);
                    InGameUi inGameUi = new InGameUi();
                    inGameUi.run(scanner.nextLine(), serverFacade);
                }
                gameNumber ++;
            }
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void observe(String gameID) {
        Integer game;

        try {
            game = Integer.parseInt(gameID);
        } catch (NumberFormatException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: User error. Input gameID as int.");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);

            return;
        }

        try {
            Games gamesRecord = serverFacade.listGames();
            List<GameDataShort> games = gamesRecord.games();

            Integer gameNumber = 1;
            for (GameDataShort gameData : games) {
                if (gameNumber.equals(game)) {
                    serverFacade.observeGame(gameData.gameID());
                    // enter InGameUi
                    Scanner scanner = new Scanner(System.in);
                    InGameUi inGameUi = new InGameUi();
                    inGameUi.run(scanner.nextLine(), serverFacade);
                }
                gameNumber ++;
            }
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private boolean logout() {
        boolean loggedIn = true;

        try {
            serverFacade.logout();
            loggedIn = false;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        if (!loggedIn) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Successful logout");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            System.out.print("[LOGGED OUT] >>> ");
        }
        return loggedIn;
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
        System.out.print("[LOGGED IN] >>> ");
    }

    private void writeHelpText(String command, String description) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(command);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
        System.out.println(description);
    }
}
