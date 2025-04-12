package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class Board {
    public void drawBoard(ChessBoard board, String playerColor) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

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

        for (int i = 8; i >= 1; i--) {
            if (i % 2 == 0) {
                System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                drawPiece(row, i, chessBoard);
            } else {
                System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                drawPiece(row, i, chessBoard);
            }
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void drawRowWhite2(Integer row, ChessBoard chessBoard) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        for (int i = 8; i >= 1; i--) {
            if (i % 2 == 0) {
                System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                drawPiece(row, i, chessBoard);
            } else {
                System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                drawPiece(row, i, chessBoard);
            }
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

        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 1) {
                System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                drawPiece(row, i, chessBoard);
            } else {
                System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                drawPiece(row, i, chessBoard);
            }
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void drawRowBlack2(Integer row, ChessBoard chessBoard) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 1) {
                System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                drawPiece(row, i, chessBoard);
            } else {
                System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                drawPiece(row, i, chessBoard);
            }
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
