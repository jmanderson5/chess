package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PawnMovesCalculator {

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
        PawnMovesCalculator that = (PawnMovesCalculator) o;
        return Objects.equals(legalMoves, that.legalMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(legalMoves);
    }

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {

    }
}
