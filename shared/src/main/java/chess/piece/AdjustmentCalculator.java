package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.List;

public class AdjustmentCalculator {
    public boolean adjustPosition(ChessPiece temp, ChessPiece startingPiece, ChessPosition myPosition,
                                  int row, int col, List<ChessMove> legalMoves) {
        if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { return true; }
        legalMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
        return temp != null;
    }

    public void movesModifier(int rowNext, int columnNext, ChessPiece startingPiece, ChessBoard board,
                              ChessPosition myPosition, List<ChessMove> legalMoves) {
        if (rowNext <= 8 && rowNext >= 1 && columnNext <= 8 && columnNext >= 1){
            ChessPiece temp = board.getPiece(new ChessPosition(rowNext, columnNext));
            if (temp == null) {
                legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), null));
            } else if (temp.getTeamColor() != startingPiece.getTeamColor()) {
                legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), null));
            }
        }
    }
}
