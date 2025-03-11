package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class KnightMovesCalculator {

    private List<ChessMove> legalMoves = new ArrayList<>();

    public List<ChessMove> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<ChessMove> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public KnightMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        int rowNext;
        int columnNext;

        ChessPiece startingPiece = board.getPiece(myPosition);
        AdjustmentCalculator calculator = new AdjustmentCalculator();

        // top
        // right
        rowNext = row + 2;
        columnNext = column + 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);
        // left
        columnNext = column - 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);

        // right
        // up
        columnNext = column + 2;
        rowNext = row + 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);
        // down
        rowNext = row - 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);

        // left
        // up
        columnNext = column - 2;
        rowNext = row + 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);
        // down
        rowNext = row - 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);

        // down
        // right
        rowNext = row - 2;
        columnNext = column + 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);
        columnNext = column - 1;
        calculator.movesModifier(rowNext, columnNext, startingPiece, board, myPosition, legalMoves);


        setLegalMoves(legalMoves);
    }
}
