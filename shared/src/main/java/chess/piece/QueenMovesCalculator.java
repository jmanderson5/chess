package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class QueenMovesCalculator {

    private List<ChessMove> legalMoves = new ArrayList<>();

    public List<ChessMove> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<ChessMove> legalMoves) {
        this.legalMoves = legalMoves;
    }

    private void queenMovesModifier(int rowNext, int columnNext, ChessPiece startingPiece, ChessBoard board,
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

    QueenMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        // implement QueenMovesCalculator

        setLegalMoves(legalMoves);
    }
}

