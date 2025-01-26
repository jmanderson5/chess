package chess.piece;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.List;

import static chess.ChessPiece.PieceType.BISHOP;

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

    private void calculateLegalMoves() {
        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == BISHOP) {
            BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
            setLegalMoves(bishopMoves.getLegalMoves());
        }
    }

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        setBoard(board);
        setMyPosition(myPosition);
        calculateLegalMoves();
    }
}
