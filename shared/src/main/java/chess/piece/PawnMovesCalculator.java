package chess.piece;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class PawnMovesCalculator {

    private List<ChessMove> legalMoves = new ArrayList<>();

    public List<ChessMove> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<ChessMove> legalMoves) {
        this.legalMoves = legalMoves;
    }

    private void promotePawn(ChessPosition myPosition, int rowNext, int columnNext) {
        legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), ChessPiece.PieceType.ROOK));
        legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), ChessPiece.PieceType.BISHOP));
        legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), ChessPiece.PieceType.KNIGHT));
        legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), ChessPiece.PieceType.QUEEN));
    }

    private void pawnMovesModifier(int rowNext, int columnNext, ChessPiece startingPiece, ChessBoard board,
                                     ChessPosition myPosition, boolean attack) {
        if (rowNext <= 8 && rowNext >= 1 && columnNext <= 8 && columnNext >= 1){
            ChessPiece temp = board.getPiece(new ChessPosition(rowNext, columnNext));
            if (startingPiece.getTeamColor() == ChessGame.TeamColor.BLACK && rowNext == 1 ||
                    startingPiece.getTeamColor() == ChessGame.TeamColor.WHITE && rowNext == 8) {
                if (temp == null && !attack) {
                    promotePawn(myPosition, rowNext, columnNext);
                } else if (temp != null && temp.getTeamColor() != startingPiece.getTeamColor() && attack) {
                    promotePawn(myPosition, rowNext, columnNext);
                }
            } else if (temp == null && !attack) {
                legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), null));
            } else if (temp != null && temp.getTeamColor() != startingPiece.getTeamColor() && attack) {
                legalMoves.add(new ChessMove(myPosition, new ChessPosition(rowNext, columnNext), null));
            }
        }
    }

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        int rowNext;
        int columnNext;

        ChessPiece startingPiece = board.getPiece(myPosition);

        // white
        if (startingPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            // up two
            if (row == 2) {
                rowNext = row + 2;
                columnNext = column;
                ChessPiece temp = board.getPiece(new ChessPosition(rowNext - 1, columnNext));
                if (temp == null) {
                    pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, false);
                }
            }
            // up one
            rowNext = row + 1;
            columnNext = column;
            pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, false);
            // attack
            // left
            columnNext = column - 1;
            pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, true);
            // right
            columnNext = column + 1;
            pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, true);
        }

        // black
        if (startingPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            // down two
            if (row == 7) {
                rowNext = row - 2;
                columnNext = column;
                ChessPiece temp = board.getPiece(new ChessPosition(rowNext + 1, columnNext));
                if (temp == null) {
                    pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, false);
                }
            }
            // down one
            rowNext = row - 1;
            columnNext = column;
            pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, false);
            // attack
            // left
            columnNext = column - 1;
            pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, true);
            // right
            columnNext = column + 1;
            pawnMovesModifier(rowNext, columnNext, startingPiece, board, myPosition, true);
        }

        setLegalMoves(legalMoves);
    }
}
