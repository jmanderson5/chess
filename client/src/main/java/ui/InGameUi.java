package ui;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.ResponseException;
import server.NotificationHandler;
import server.WebsocketCommunicator;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.util.Scanner;

public class InGameUi implements NotificationHandler {

    private boolean inGame = true;
    private boolean firstRun = true;
    private String input;
    private Integer gameID;
    private String playerColor;
    private WebsocketCommunicator ws;
    private final Board board = new Board();
    private ChessBoard game;

    public InGameUi(String url) {
        try {
            ws = new WebsocketCommunicator(url, this);
        } catch (ResponseException e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    public void run(String auth, Integer gameID, String playerColor)
            throws ResponseException {
        this.input = "join # [WHITE|BLACK]";
        this.gameID = gameID;
        this.playerColor = playerColor;

        // connect to WebSocket
        ws.setAuthToken(auth);
        ws.connect(gameID, playerColor);

        while (inGame) {
            String[] parts = this.input.split(" ");
            if (parts.length == 1 && parts[0].equals("redraw")) { redraw(); }
            else if (parts.length == 1 && parts[0].equals("leave")) { leave(); }
            else if (parts.length == 5 && parts[0].equals("move")) { move(parts[1], parts[3], parts[4]); }
            else if (parts.length == 1 && parts[0].equals("resign")) { resign(); }
            else if (parts.length == 2 && parts[0].equals("highlight")) { highlight(parts[1]); }
            else if (parts.length == 1 && parts[0].equals("help")) { help(); }
            else if (!firstRun) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                System.out.print("unsuccessful: try again");
                System.out.println(EscapeSequences.RESET_TEXT_COLOR);
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
                System.out.println("type help for in game commands");
                System.out.print(EscapeSequences.RESET_TEXT_COLOR);
                firstRun = false;
            }
            if (inGame) {
                getInput();
            }
        }
    }

    private void redraw() {
        board.drawBoard(game, playerColor, false, null);
    }

    private void leave() {
        try {
            ws.disconnect(gameID);
            inGame = false;
            System.out.print("[LOGGED IN] >>> ");
        } catch (Exception e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void move(String beginning, String end, String promotionPiece) {
        String[] newBeginning = beginning.split(",");
        String[] newEnd = end.split(",");

        if (newBeginning.length != 2 && newEnd.length != 2) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: invalid input");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            return;
        }

        try {
            // get start position
            int beginningRow = Integer.parseInt(newBeginning[1]), beginningCol = getColNumber(newBeginning[0]);
            ChessPosition startPosition = new ChessPosition(beginningRow, beginningCol);
            // get end position
            int endRow = Integer.parseInt(newEnd[1]), endCol = getColNumber(newEnd[0]);
            ChessPosition endPosition = new ChessPosition(endRow, endCol);
            // get promotion piece
            ChessPiece.PieceType pieceType = getPieceType(promotionPiece);

            ChessMove move = new ChessMove(startPosition, endPosition, pieceType);
            ws.makeMove(gameID, move);
        } catch (NumberFormatException ex) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: invalid input");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        } catch (Exception e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private int getColNumber(String letterCol) {
        return switch (letterCol) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            case "D" -> 4;
            case "E" -> 5;
            case "F" -> 6;
            case "G" -> 7;
            case "H" -> 8;
            default -> throw new IllegalStateException("Unexpected value: " + letterCol);
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
        try {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            System.out.print("RESIGNATION CONFIRMATION: ");
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.print("please type ");
            System.out.print(EscapeSequences.SET_TEXT_ITALIC);
            System.out.print("CONFIRM ");
            System.out.print(EscapeSequences.RESET_TEXT_ITALIC);
            System.out.println("to confirm");
            System.out.print("[CONFIRM] >>> ");
            getInput();
            if (input.equals("CONFIRM")) {
                ws.resign(gameID);
            }
        } catch (Exception e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void highlight(String selectedPiece) {
        String[] piece = selectedPiece.split(",");

        if (piece.length != 2) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: invalid input");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
            return;
        }

        try {
            // get start position
            int beginningRow = Integer.parseInt(piece[1]), beginningCol = getColNumber(piece[0]);
            ChessPosition piecePosition = new ChessPosition(beginningRow, beginningCol);
            board.drawBoard(game, playerColor, true, piecePosition);
        } catch (NumberFormatException ex) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: invalid input");
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        } catch (Exception e) {
            System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
            System.out.println("Error: " + e.getMessage());
            System.out.print(EscapeSequences.RESET_TEXT_COLOR);
        }
    }

    private void help() {
        writeHelpText("redraw", " - chessboard");
        writeHelpText("leave", " - chess game");
        writeHelpText("move A,# to A,# [QUEEN|BISHOP|...|NULL}", " - to make move");
        writeHelpText("resign", " - from chess game");
        writeHelpText("highlight A,#", " - legal moves");
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
        game = serverMessage.getGame().game().getBoard();
        board.drawBoard(game, playerColor, false, null);
    }

    @Override
    public void error(ErrorMessage message) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.println(message.getMessage());
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
    }

    @Override
    public void notification(NotificationMessage message) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_MAGENTA);
        System.out.println(message.getMessage());
        System.out.print(EscapeSequences.RESET_TEXT_COLOR);
    }
}
