package ui;

import exception.ResponseException;
import model.handler.GameDataShort;
import model.handler.Games;
import server.ServerFacade;

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

        try {
            serverFacade.createGame(game);
            created = true;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        if (created) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Created '" + game + "'");
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
            Integer gameNumber = 1;
            for (GameDataShort game : games) {
                System.out.println(gameNumber + ") " + game.gameName() + " Black user:" +
                        game.blackUsername() + " White user:" + game.whiteUsername());
                gameNumber ++;
            }
            System.out.println(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private int join(String gameID, String playerColor) {
        boolean joined = false;
        boolean gameExists = false;
        Integer game = null;
        try {
            game = Integer.parseInt(gameID);
        } catch (NumberFormatException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: User error input gameID as int");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);

            return 1;
        }

        try {
            Games gamesRecord = serverFacade.listGames();
            List<GameDataShort> games = gamesRecord.games();

            Integer gameNumber = 1;
            for (GameDataShort gameData : games) {
                if (gameNumber.equals(game)) {
                    serverFacade.joinGame(playerColor, gameData.gameID());
                    gameExists = true;
                }
                gameNumber ++;
            }
            joined = true;
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            gameExists = true;
        }

        if (joined && gameExists) {
            drawBoard(playerColor, game);
        } else {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Game does not exist");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        return 0;
    }

    private int observe(String gameID) {
        Integer game = null;
        try {
            game = Integer.parseInt(gameID);
        } catch (NumberFormatException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: User error input gameID as int");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);

            return 1;
        }
        boolean gameExists = false;
        Games gamesRecord = null;

        try {
            gamesRecord = serverFacade.listGames();
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println(e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        assert gamesRecord != null;
        List<GameDataShort> games = gamesRecord.games();

        Integer gameNumber = 1;
        for (GameDataShort gameData : games) {
            assert game != null;
            if (game.equals(gameNumber)) {
                gameExists = true;
            }
            gameNumber ++;
        }

        if (gameExists) {
            drawBoard("WHITE", 1234);
        } else {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: Game does not exist");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }

        return 0;
    }

    private void drawBoard(String playerColor, Integer gameID) {
        if (playerColor.equals("WHITE")) {
            // draw white board perspective
            whiteBoard();
        } else {
            // draw black board perspective
            blackBoard();
        }
    }

    private void whiteBoard() {
        System.out.println();

        // header
        drawLetters();

        // eight row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 8 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_QUEEN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_KING);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 8 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // seventh row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 7 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 7 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // sixth row
        emptyRow2(6);

        // fifth row
        emptyRow1(5);

        // fourth row
        emptyRow2(4);

        // third row
        emptyRow1(3);

        // second row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 2 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 2 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // first row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 1 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_QUEEN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_KING);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 1 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // footer
        drawLetters();

        // reset
        System.out.println(EscapeSequences.RESET_TEXT_COLOR);
    }

    private void blackBoard() {
        System.out.println();

        // header
        drawLettersReversed();

        // eight row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 8 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_KING);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_QUEEN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 8 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // seventh row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 2 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.WHITE_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 2 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // third row
        emptyRow2(3);

        // fourth row
        emptyRow1(4);

        // fifth row
        emptyRow2(5);

        // sixth row
        emptyRow1(6);

        // seventh row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 7 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_PAWN);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 7 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // Eighth row
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 8 ");
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_KING);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_QUEEN);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_BISHOP);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.BLACK_KNIGHT);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.BLACK_ROOK);
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" 8 ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);

        // footer
        drawLettersReversed();

        // reset
        System.out.println(EscapeSequences.RESET_TEXT_COLOR);
    }

    private void drawLetters() {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(" a ");
        System.out.print(" b ");
        System.out.print(" c ");
        System.out.print(" d ");
        System.out.print(" e ");
        System.out.print(" f ");
        System.out.print(" g ");
        System.out.print(" h ");
        System.out.print(EscapeSequences.EMPTY);
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void drawLettersReversed() {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(" h ");
        System.out.print(" g ");
        System.out.print(" f ");
        System.out.print(" e ");
        System.out.print(" d ");
        System.out.print(" c ");
        System.out.print(" b ");
        System.out.print(" a ");
        System.out.print(EscapeSequences.EMPTY);
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void emptyRow1(Integer row) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void emptyRow2(Integer row) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        System.out.print(EscapeSequences.EMPTY);
        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print(EscapeSequences.EMPTY);

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
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
    }

    private void writeHelpText(String command, String description) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.print(command);
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
        System.out.println(description);
    }
}
