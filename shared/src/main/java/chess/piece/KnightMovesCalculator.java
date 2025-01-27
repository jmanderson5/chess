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

    private void knightMovesModifier(int rowNext, int columnNext, ChessPiece startingPiece, ChessBoard board,
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

    public KnightMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        int rowNext;
        int columnNext;

        ChessPiece startingPiece = board.getPiece(myPosition);

        // top
        // right
        rowNext = row + 2;
        columnNext = column + 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);
        // left
        columnNext = column - 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);

        // right
        // up
        columnNext = column + 2;
        rowNext = row + 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);
        // down
        rowNext = row - 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);

        // left
        // up
        columnNext = column - 2;
        rowNext = row + 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);
        // down
        rowNext = row - 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);

        // down
        // right
        rowNext = row - 2;
        columnNext = column + 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);
        columnNext = column - 1;
        knightMovesModifier(rowNext, columnNext, startingPiece, board, myPosition);


        setLegalMoves(legalMoves);
    }
}
