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

    QueenMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        ChessPiece startingPiece = board.getPiece(myPosition);

        // up
        for (int i = row + 1; i <= 8; i++) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, column));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, column), null));
            if (temp != null) { break; }
        }
        // top right
        for (int i = row + 1, j = column + 1; i <= 8 && j <= 8; i++, j++) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) { break; }
        }
        // right
        for (int j = column + 1; j <= 8; j++) {
            ChessPiece temp = board.getPiece(new ChessPosition(row, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(row, j), null));
            if (temp != null) { break; }
        }
        // bottom right
        for (int i = row - 1, j = column + 1; i >= 1 && j <= 8; i--, j++) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) { break; }
        }
        // down
        for (int i = row - 1; i >= 1; i--) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, column));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, column), null));
            if (temp != null) { break; }
        }
        // bottom left
        for (int i = row - 1, j = column - 1; i >= 1 && j >= 1; i--, j--) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) { break; }
        }
        // left
        for (int j = column - 1; j >= 1; j--) {
            ChessPiece temp = board.getPiece(new ChessPosition(row, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(row, j), null));
            if (temp != null) { break; }
        }
        // top left
        for (int i = row + 1, j = column - 1; i <= 8 && j >= 1; i++, j--) {
            ChessPiece temp = board.getPiece(new ChessPosition(i, j));
            if (temp != null && temp.getTeamColor() == startingPiece.getTeamColor()) { break; }
            legalMoves.add(new ChessMove(myPosition, new ChessPosition(i, j), null));
            if (temp != null) { break; }
        }

        setLegalMoves(legalMoves);
    }
}

