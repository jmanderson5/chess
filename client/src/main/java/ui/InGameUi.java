package ui;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.ResponseException;
import model.GameData;
import server.NotificationHandler;
import server.ServerFacade;
import websocket.messages.LoadGameMessage;

import java.util.Scanner;

public class InGameUi implements NotificationHandler {

    private boolean inGame = true;
    private String input;
    private ServerFacade serverFacade;
    Integer gameID;
    String playerColor;
    NotificationHandler notificationHandler;

    public InGameUi() {
        this.notificationHandler = this;
    }

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
            else if (parts.length == 5 && parts[0].equals("move")) { move(parts[1], parts[3], parts[4]); }
            else if (parts.length == 1 && parts[0].equals("resign")) { resign(); }
            else if (parts.length == 1 && parts[0].equals("highlight")) { highlight(); }
            else if (parts.length == 1 && parts[0].equals("help")) { help(); }
            else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                System.out.print("unsuccessful: try again");
                System.out.println(EscapeSequences.RESET_TEXT_COLOR);
            }
            if (inGame) {
                getInput();
            }
        }
    }

    private void redraw() {
        try {
            serverFacade.redraw(gameID, playerColor);
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void leave() {
        try {
            serverFacade.leave(gameID);
            inGame = false;
            System.out.print("[LOGGED IN] >>> ");
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void move(String beginning, String end, String promotionPiece) {
        String[] newBeginning = beginning.split(",");
        String[] newEnd = end.split(",");

        // get game
        GameData game;

        // get start position
        int beginningRow = Integer.parseInt(newBeginning[0]), beginningCol = getColNumber(newBeginning[1]);
        ChessPosition startPosition = new ChessPosition(beginningRow, beginningCol);
        // get end position
        int endRow = Integer.parseInt(newEnd[0]), endCol = getColNumber(newEnd[1]);
        ChessPosition endPosition = new ChessPosition(endRow, endCol);
        // get promotion piece
        ChessPiece.PieceType pieceType = getPieceType(promotionPiece);

        try {
            ChessMove move = new ChessMove(startPosition, endPosition, pieceType);
            serverFacade.makeMove(gameID, move);
            redraw();
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private int getColNumber(String LetterCol) {
        return switch (LetterCol) {
            case "A" -> 8;
            case "B" -> 7;
            case "C" -> 6;
            case "D" -> 5;
            case "E" -> 4;
            case "F" -> 3;
            case "G" -> 2;
            case "H" -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + LetterCol);
        };
    }

    private ChessPiece.PieceType getPieceType(String promotionPiece) {
        return switch (promotionPiece) {
            case "PAWN" -> ChessPiece.PieceType.PAWN;
            case "QUEEN" -> ChessPiece.PieceType.QUEEN;
            case "KNIGHT" -> ChessPiece.PieceType.KNIGHT;
            case "ROOK" -> ChessPiece.PieceType.ROOK;
            case "BISHOP" -> ChessPiece.PieceType.BISHOP;
            default -> null;
        };
    }

    private void resign() {
    }

    private void highlight() {
    }

    private void help() {
        writeHelpText("redraw", " - chessboard");
        writeHelpText("leave", " - chess game");
        writeHelpText("move #,A to #,A [QUEEN|BISHOP|...|NULL}", " - to make move");
        writeHelpText("resign", " - from chess game");
        writeHelpText("highlight", " - legal moves");
        writeHelpText("help", " - with possible commands");
        System.out.println(EscapeSequences.RESET_TEXT_COLOR);
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

    @Override
    public void loadGame(LoadGameMessage serverMessage) {
        Board board = new Board();
        ChessBoard game = serverMessage.getGame().game().getBoard();
        board.drawBoard(game, playerColor);
    }

    @Override
    public void error() {

    }

    @Override
    public void notification() {

    }
}
