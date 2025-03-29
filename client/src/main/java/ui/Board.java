package ui;

public class Board {
    public void drawBoard(String playerColor, Integer gameID) {
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

        whiteTop();

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

    private void whiteTop() {
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
    }

    private void blackBoard() {
        System.out.println();

        // header
        drawLettersReversed();

        blackTop();

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

    private void blackTop() {
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
}
