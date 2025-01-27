package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.List;
import java.util.Objects;

import static chess.ChessPiece.PieceType.*;

public class PieceMovesCalculator {

    private List<ChessMove> legalMoves;
    private ChessBoard board;
    private ChessPosition myPosition;

    public List<ChessMove> getLegalMoves() {
        return legalMoves;
    }

    private void setLegalMoves(List<ChessMove> legalMoves) {
        this.legalMoves = legalMoves;
    }

    private void setBoard(ChessBoard board) {
        this.board = board;
    }

    private void setMyPosition(ChessPosition myPosition) {
        this.myPosition = myPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceMovesCalculator that = (PieceMovesCalculator) o;
        return Objects.equals(legalMoves, that.legalMoves) && Objects.equals(board, that.board) && Objects.equals(myPosition, that.myPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legalMoves, board, myPosition);
    }

    private void calculateLegalMoves() {
        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == BISHOP) {
            BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
            setLegalMoves(bishopMoves.getLegalMoves());
        }
        if (piece.getPieceType() == KING) {
            KingMovesCalculator kingMoves = new KingMovesCalculator(board, myPosition);
            setLegalMoves(kingMoves.getLegalMoves());
        }
        if (piece.getPieceType() == KNIGHT) {
            KnightMovesCalculator knightMoves = new KnightMovesCalculator(board, myPosition);
            setLegalMoves(knightMoves.getLegalMoves());
        }
    }

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> frank;
        setBoard(board);
        setMyPosition(myPosition);
        calculateLegalMoves();
    }
}
