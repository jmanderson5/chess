package ui;

import chess.*;

import java.util.Collection;

public class Board {
    private boolean moves;
    private ChessPosition piecePosition;
    Collection<ChessMove> legalMoves;

    public void drawBoard(ChessBoard board, String playerColor, boolean moves, ChessPosition piecePosition) {

        this.moves = moves;
        if (moves) {
            this.piecePosition = piecePosition;
            if (piecePosition != null) {
                ChessGame game = new ChessGame();
                game.setBoard(board);
                this.legalMoves = game.validMoves(piecePosition);
            }
        }

        if (playerColor.equals("WHITE")) {
            // draw white board perspective
            whiteBoard(board);
        } else {
            // draw black board perspective
            blackBoard(board);
        }
    }

    private void whiteBoard(ChessBoard chessBoard) {
        System.out.println();

        // header
        drawLetters();

        for (int i = 8; i >= 1; i--) {
            if (i % 2 == 0) {
                drawRowWhite1(i, chessBoard);
            } else {
                drawRowWhite2(i, chessBoard);
            }
        }

        // footer
        drawLetters();

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

    private void drawRowWhite1(Integer row, ChessBoard chessBoard) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        for (int i = 1; i <= 8; i++) {
            drawRowHelper(row, i, chessBoard, EscapeSequences.SET_BG_COLOR_LIGHT_GREY,
                    EscapeSequences.SET_BG_COLOR_DARK_GREY, EscapeSequences.SET_BG_COLOR_GREEN,
                    EscapeSequences.SET_BG_COLOR_DARK_GREEN);
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void drawRowHelper(int row, int col, ChessBoard chessBoard, String color1, String color2, String green1,
                               String green2) {
        if (col % 2 == 0) {
            if (moves) {
                ChessPosition chessPiece = new ChessPosition(row, col);
                drawMoves(chessBoard, row, col, chessPiece, color2, green2);
            } else {
                System.out.print(color2);
                drawPiece(row, col, chessBoard);
            }
        } else {
            if (moves) {
                ChessPosition chessPiece = new ChessPosition(row, col);
                drawMoves(chessBoard, row, col, chessPiece, color1, green1);
            } else {
                System.out.print(color1);
                drawPiece(row, col, chessBoard);
            }
        }
    }

    private void drawMoves(ChessBoard board, Integer row, int col, ChessPosition chessPiece,
                                 String grayHugh, String greenHugh) {
        boolean pieceDrawn = false;

        for (ChessMove move : legalMoves) {
            if (pieceDrawn) { break; }

            if (chessPiece.equals(move.getEndPosition())) {
                System.out.print(greenHugh);
                drawPiece(row, col, board);
                pieceDrawn = true;
            } else if (chessPiece.equals(piecePosition)) {
                System.out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
                System.out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
                drawPiece(row, col, board);
                System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
                pieceDrawn = true;
            }
        }

        if (!pieceDrawn) {
            System.out.print(grayHugh);
            drawPiece(row, col, board);
        }
    }

    private void drawRowWhite2(Integer row, ChessBoard chessBoard) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        for (int i = 1; i <= 8; i++) {
            drawRowHelper(row, i, chessBoard, EscapeSequences.SET_BG_COLOR_DARK_GREY,
                    EscapeSequences.SET_BG_COLOR_LIGHT_GREY, EscapeSequences.SET_BG_COLOR_DARK_GREEN,
                    EscapeSequences.SET_BG_COLOR_GREEN);
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void blackBoard(ChessBoard chessBoard) {
        System.out.println();

        // header
        drawLettersReversed();

        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 1) {
                drawRowBlack1(i, chessBoard);
            } else {
                drawRowBlack2(i, chessBoard);
            }
        }

        // footer
        drawLettersReversed();

        // reset
        System.out.println(EscapeSequences.RESET_TEXT_COLOR);
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

    private void drawRowBlack1(Integer row, ChessBoard chessBoard) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        for (int i = 8; i >= 1; i--) {
            drawRowHelper(row, i, chessBoard, EscapeSequences.SET_BG_COLOR_DARK_GREY,
                    EscapeSequences.SET_BG_COLOR_LIGHT_GREY, EscapeSequences.SET_BG_COLOR_DARK_GREEN,
                    EscapeSequences.SET_BG_COLOR_GREEN);
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void drawRowBlack2(Integer row, ChessBoard chessBoard) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        for (int i = 8; i >= 1; i--) {
            drawRowHelper(row, i, chessBoard, EscapeSequences.SET_BG_COLOR_LIGHT_GREY,
                    EscapeSequences.SET_BG_COLOR_DARK_GREY, EscapeSequences.SET_BG_COLOR_GREEN,
                    EscapeSequences.SET_BG_COLOR_DARK_GREEN);
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void drawPiece(Integer row, int col, ChessBoard chessBoard) {
        ChessPiece chessPiece = chessBoard.getPiece(new ChessPosition(row, col));

        if (chessPiece == null) {
            System.out.print(EscapeSequences.EMPTY);
        } else if (chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                System.out.print(EscapeSequences.WHITE_BISHOP);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING) {
                System.out.print(EscapeSequences.WHITE_KING);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
                System.out.print(EscapeSequences.WHITE_ROOK);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                System.out.print(EscapeSequences.WHITE_KNIGHT);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                System.out.print(EscapeSequences.WHITE_QUEEN);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                System.out.print(EscapeSequences.WHITE_PAWN);
            }
        } else if (chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                System.out.print(EscapeSequences.BLACK_BISHOP);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING) {
                System.out.print(EscapeSequences.BLACK_KING);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
                System.out.print(EscapeSequences.BLACK_ROOK);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                System.out.print(EscapeSequences.BLACK_KNIGHT);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                System.out.print(EscapeSequences.BLACK_QUEEN);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                System.out.print(EscapeSequences.BLACK_PAWN);
            }
        }
    }
}
