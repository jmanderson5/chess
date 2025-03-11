package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class RookMovesCalculator {

    private List<ChessMove> legalMoves = new ArrayList<>();

    public List<ChessMove> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<ChessMove> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public RookMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        ChessPiece startingPiece = board.getPiece(myPosition);
        AdjustmentCalculator calculator = new AdjustmentCalculator();

        // up
        for (int i = row + 1; i <= 8; i++) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, column));
            if (calculator.adjustPosition(temp, startingPiece, myPosition, i, column, legalMoves)) { break; }
        }
        // right
        for (int j = column + 1; j <= 8; j++) {
            ChessPiece temp = board.getPiece(new ChessPosition(row, j));
            if (calculator.adjustPosition(temp, startingPiece, myPosition, row, j, legalMoves)) { break; }
        }
        // down
        for (int i = row - 1; i >= 1; i--) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, column));
            if (calculator.adjustPosition(temp, startingPiece, myPosition, i, column, legalMoves)) { break; }
        }
        // left
        for (int j = column - 1; j >= 1; j--) {
            ChessPiece temp = board.getPiece(new ChessPosition(row, j));
            if (calculator.adjustPosition(temp, startingPiece, myPosition, row, j, legalMoves)) { break; }
        }

        setLegalMoves(legalMoves);
    }
}
