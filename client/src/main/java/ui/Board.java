package ui;

import chess.*;

import java.util.List;

public class Board {
    private boolean moves;
    private ChessPosition piecePosition;
    List<ChessMove> legalMoves;

    public void drawBoard(ChessBoard board, String playerColor, boolean moves, ChessPosition piecePosition) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

        this.moves = moves;
        if (moves) {
            this.piecePosition = piecePosition;
            if (piecePosition != null) {
                this.legalMoves = board.getPiece(piecePosition).pieceMoves(board, piecePosition);
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

        for (int i = 8; i >= 1; i--) {
            if (i % 2 == 0) {
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesDarkGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                    drawPiece(row, i, chessBoard);
                }
            } else {
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesLightGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                    drawPiece(row, i, chessBoard);
                }
            }
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private void movesLightGreen(ChessBoard board, Integer row, int col, ChessPosition chessPiece) {
        boolean pieceDrawn = false;

        for (ChessMove move : legalMoves) {
            if (pieceDrawn) { break; }

            if (chessPiece.equals(move.getEndPosition())) {
                System.out.print(EscapeSequences.SET_BG_COLOR_GREEN);
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
            System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            drawPiece(row, col, board);
        }
    }

    private void movesDarkGreen(ChessBoard board, int row, int col, ChessPosition chessPiece) {
        boolean pieceDrawn = false;

        for (ChessMove move : legalMoves) {
            if (pieceDrawn) { break; }

            if (chessPiece.equals(move.getEndPosition())) {
                System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
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
            System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
            drawPiece(row, col, board);
        }
    }

    private void drawRowWhite2(Integer row, ChessBoard chessBoard) {
        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        for (int i = 8; i >= 1; i--) {
            if (i % 2 == 0) {
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesLightGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                    drawPiece(row, i, chessBoard);
                }
            } else {
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesDarkGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                    drawPiece(row, i, chessBoard);
                }
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
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesDarkGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                    drawPiece(row, i, chessBoard);
                }
            } else {
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesLightGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                    drawPiece(row, i, chessBoard);
                }
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
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesLightGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                    drawPiece(row, i, chessBoard);
                }
            } else {
                if (moves) {
                    ChessPosition chessPiece = new ChessPosition(row, i);
                    movesDarkGreen(chessBoard, row, i, chessPiece);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                    drawPiece(row, i, chessBoard);
                }
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
