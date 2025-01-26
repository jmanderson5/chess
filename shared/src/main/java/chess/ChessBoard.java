package chess;

import java.util.Arrays;
import java.util.Objects;

import static chess.ChessGame.*;
import static chess.ChessPiece.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] squares = new ChessPiece[8][8];

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[8][8];

        // Add bottom row
        pieces(1, TeamColor.WHITE);

        // Add second row pawns
        addPawns(2, TeamColor.WHITE);

        // Add top row
        pieces(8, TeamColor.BLACK);

        // Add seventh row pawns
        addPawns(7, TeamColor.BLACK);
    }

    private void addPawns(int row, TeamColor color) {
        for (int col = 1; col <= 8; col++) {
            addPiece(new ChessPosition(row, col), new ChessPiece(color, PieceType.PAWN));
        }
    }

    private void pieces(int row, TeamColor color) {
        addPiece(new ChessPosition(row, 1), new ChessPiece(color, PieceType.ROOK));
        addPiece(new ChessPosition(row, 2), new ChessPiece(color, PieceType.KNIGHT));
        addPiece(new ChessPosition(row, 3), new ChessPiece(color, PieceType.BISHOP));
        addPiece(new ChessPosition(row, 4), new ChessPiece(color, PieceType.QUEEN));
        addPiece(new ChessPosition(row, 5), new ChessPiece(color, PieceType.KING));
        addPiece(new ChessPosition(row, 6), new ChessPiece(color, PieceType.BISHOP));
        addPiece(new ChessPosition(row, 7), new ChessPiece(color, PieceType.KNIGHT));
        addPiece(new ChessPosition(row, 8), new ChessPiece(color, PieceType.ROOK));
    }

}
