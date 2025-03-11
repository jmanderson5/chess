package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KingMovesCalculator {

    private List<ChessMove> legalMoves = new ArrayList<>();

    public List<ChessMove> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<ChessMove> legalMoves) {
        this.legalMoves = legalMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KingMovesCalculator that = (KingMovesCalculator) o;
        return Objects.equals(legalMoves, that.legalMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(legalMoves);
    }

    public KingMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        ChessPiece startingPiece = board.getPiece(myPosition);
        KnightMovesCalculator calculator = new KnightMovesCalculator(board, myPosition);

        // up one
        calculator.movesModifier(row + 1, column, startingPiece, board, myPosition);
        // topRight
        calculator.movesModifier(row + 1, column + 1, startingPiece, board, myPosition);
        // topLeft
        calculator.movesModifier(row + 1, column - 1, startingPiece, board, myPosition);
        // left
        calculator.movesModifier(row, column - 1, startingPiece, board, myPosition);
        // right
        calculator.movesModifier(row, column + 1, startingPiece, board, myPosition);
        // down one
        calculator.movesModifier(row - 1, column, startingPiece, board, myPosition);
        // bottomRight
        calculator.movesModifier(row - 1, column + 1, startingPiece, board, myPosition);
        // bottomLeft
        calculator.movesModifier(row - 1, column - 1, startingPiece, board, myPosition);

        setLegalMoves(legalMoves);
    }
}
