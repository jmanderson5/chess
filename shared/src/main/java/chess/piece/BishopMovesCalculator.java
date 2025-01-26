package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class BishopMovesCalculator {

    private List<ChessMove> legalMoves = new ArrayList<>();

    public List<ChessMove> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<ChessMove> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int column = myPosition.getColumn();
        int row = myPosition.getRow();

        // top right
        for (int i = column + 1, j = row + 1; i <= 8 && j <= 8; i++, j++) {
            legalMoves.add(0, new ChessMove(new ChessPosition(i, j), myPosition, ChessPiece.PieceType.BISHOP));
        }
        // top left
        for (int i = column + 1, j = row - 1; i <= 8 && j >= 0; i++, j--) {
            legalMoves.add(0, new ChessMove(new ChessPosition(i, j), myPosition, ChessPiece.PieceType.BISHOP));
        }
        // bottom right
        for (int i = column - 1, j = row + 1; i >= 0 && j <= 8; i--, j++) {
            legalMoves.add(0, new ChessMove(new ChessPosition(i, j), myPosition, ChessPiece.PieceType.BISHOP));
        }
        // bottom left
        for (int i = column - 1, j = row - 1; i >= 0 && j >= 0; i--, j--) {
            legalMoves.add(0, new ChessMove(new ChessPosition(i, j), myPosition, ChessPiece.PieceType.BISHOP));
        }

        setLegalMoves(legalMoves);
    }
}
