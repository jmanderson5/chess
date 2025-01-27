package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BishopMovesCalculator {

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
        BishopMovesCalculator that = (BishopMovesCalculator) o;
        return Objects.equals(legalMoves, that.legalMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(legalMoves);
    }

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        ChessPiece temp;
        ChessPiece startingPiece = board.getPiece(new ChessPosition(row, column));

        // top right
        for (int i = row + 1, j = column + 1; i <= 8 && j <= 8; i++, j++) {
            temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) {
                break;
            }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) {
                break;
            }
        }
        // top left
        for (int i = row + 1, j = column - 1; i <= 8 && j > 0; i++, j--) {
            temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) {
                break;
            }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) {
                break;
            }
        }
        // bottom right
        for (int i = row - 1, j = column + 1; i > 0 && j <= 8; i--, j++) {
            temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) {
                break;
            }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) {
                break;
            }
        }
        // bottom left
        for (int i = row - 1, j = column - 1; i > 0 && j > 0; i--, j--) {
            temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) {
                break;
            }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) {
                break;
            }
        }

        setLegalMoves(legalMoves);
    }
}
