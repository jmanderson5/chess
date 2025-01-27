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

    private void kingMovesModifier(int rowNext, int columnNext, ChessPiece startingPiece, ChessBoard board,
                                   ChessPosition myPosition) {
        if (rowNext <= 8 && rowNext >= 1 && columnNext <= 8 && columnNext >= 1){
            ChessPiece temp = board.getPiece(new ChessPosition(rowNext, columnNext));
            if (temp == null) {
                legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), null));
            } else if (temp.getTeamColor() != startingPiece.getTeamColor()) {
                legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), null));
            }
        }
    }

    public KingMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        ChessPiece startingPiece = board.getPiece(myPosition);

        // up one
        kingMovesModifier(row + 1, column, startingPiece, board, myPosition);
        // topRight
        kingMovesModifier(row + 1, column + 1, startingPiece, board, myPosition);
        // topLeft
        kingMovesModifier(row + 1, column - 1, startingPiece, board, myPosition);
        // left
        kingMovesModifier(row, column - 1, startingPiece, board, myPosition);
        // right
        kingMovesModifier(row, column + 1, startingPiece, board, myPosition);
        // down one
        kingMovesModifier(row - 1, column, startingPiece, board, myPosition);
        // bottomRight
        kingMovesModifier(row - 1, column + 1, startingPiece, board, myPosition);
        // bottomLeft
        kingMovesModifier(row - 1, column - 1, startingPiece, board, myPosition);

        setLegalMoves(legalMoves);
    }
}
