package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor teamTurn;

    public ChessGame() {
        this.board = new ChessBoard();
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> chessMoveCollection = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        ChessPiece ogPiece;
        ChessPiece capturedPiece;

        for (ChessMove chessMove : chessMoveCollection) {
            ogPiece = board.getPiece(chessMove.getStartPosition());
            capturedPiece = board.getPiece(chessMove.getEndPosition());
            // make move to check validity and add to validMoves
            try {
                TeamColor ogColor = teamTurn;
                setTeamTurn(board.getPiece(startPosition).getTeamColor());
                makeMove(chessMove);
                // switch back color
                setTeamTurn(ogColor);
                // don't add if in check!
                if (!isInCheck(teamTurn)) {
                    validMoves.add(chessMove);
                }
            } catch (InvalidMoveException e) {}
            // return board to original state
            undoMove(chessMove, ogPiece, capturedPiece);
        }

        return validMoves;
    }

    private boolean canMakeMove(ChessMove move) {
        List<ChessMove> chessMoveList = board.getPiece(move.getStartPosition()).pieceMoves(board, move.getStartPosition());
        for (ChessMove chessMove : chessMoveList) {
            if (chessMove.getEndPosition().getRow() == move.getEndPosition().getRow() &&
                chessMove.getEndPosition().getColumn() == move.getEndPosition().getColumn()) {
                return true;
            }
        }
        return false;
    }

    private void undoMove(ChessMove move, ChessPiece ogPiece, ChessPiece capturedPiece) {
        // return board to original state
        board.addPiece(move.getStartPosition(), ogPiece);
        if (capturedPiece != null) {
            board.addPiece(move.getEndPosition(), capturedPiece);
        } else {
            board.addPiece(move.getEndPosition(), null);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece ogPiece = board.getPiece(move.getStartPosition());
        ChessPiece capturedPiece = board.getPiece((move.getEndPosition()));

        if (board.getPiece(move.getStartPosition()) != null && canMakeMove(move) &&
                board.getPiece(move.getStartPosition()).getTeamColor() == teamTurn) {
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            } else {
                board.addPiece(move.getEndPosition(), new ChessPiece(teamTurn, move.getPromotionPiece()));
            }
            board.addPiece(move.getStartPosition(), null);
            // is the game in check now?
            if (isInCheck(teamTurn)) {
                undoMove(move, ogPiece, capturedPiece);
                throw new InvalidMoveException();
            }
            // switch team color if move is valid
            if (teamTurn == TeamColor.BLACK) {
                setTeamTurn(TeamColor.WHITE);
            } else {
                setTeamTurn(TeamColor.BLACK);
            }
        } else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean inCheck = false;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition myPosition = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(myPosition);

                inCheck = check(piece, teamColor, myPosition);
                if (inCheck) {
                    return true;
                }
            }
        }
        return inCheck;
    }

    private boolean check(ChessPiece piece, TeamColor teamColor, ChessPosition myPosition) {
        List<ChessMove> chessMoveList;

        if (piece != null && piece.getTeamColor() != teamColor) {
            chessMoveList = piece.pieceMoves(board, myPosition);
            for (ChessMove move : chessMoveList) {
                ChessPiece endPiece = board.getPiece(move.getEndPosition());
                if (endPiece != null && endPiece.getPieceType() == ChessPiece.PieceType.KING &&
                        endPiece.getTeamColor() == teamColor) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean inCheckmate = false;
        if (!isInCheck(teamColor)) {
            return false;
        }
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition myPosition = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(myPosition);

                if (piece != null && piece.getTeamColor() == teamColor) {
                    if (!checkmate(piece, myPosition)) { return false; }
                }
            }
        }
        return true;
    }

    private boolean checkmate(ChessPiece piece, ChessPosition myPosition) {
        List<ChessMove> chessMoveList;
        ChessPiece ogPiece, capturedPiece;
        chessMoveList = piece.pieceMoves(board, myPosition);

        for (ChessMove chessMove : chessMoveList) {
            ogPiece = board.getPiece(chessMove.getStartPosition());
            capturedPiece = board.getPiece(chessMove.getEndPosition());
            try {
                makeMove(chessMove);
                undoMove(chessMove, ogPiece, capturedPiece);
                return false;
            } catch (InvalidMoveException ignore) {}
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean inStalemate = !isInCheckmate(teamColor);

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition myPosition = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(myPosition);
                List<ChessMove> chessMoveList;
                ChessPiece ogPiece, capturedPiece;

                inStalemate = stalemate(piece, teamColor, myPosition, inStalemate);
                if (!inStalemate) { return false; }
            }
        }
        return inStalemate;
    }

    private boolean stalemate(ChessPiece piece, TeamColor teamColor, ChessPosition myPosition, boolean inStalemate) {
        List<ChessMove> chessMoveList;
        ChessPiece ogPiece, capturedPiece;

        if (piece != null && piece.getTeamColor() == teamColor) {
            chessMoveList = piece.pieceMoves(board, myPosition);
            // if there are possible moves to be made return false
            for (ChessMove chessMove : chessMoveList) {
                ogPiece = board.getPiece(chessMove.getStartPosition());
                capturedPiece = board.getPiece(chessMove.getEndPosition());
                try {
                    teamTurn = teamColor;
                    makeMove(chessMove);
                    undoMove(chessMove, ogPiece, capturedPiece);
                    return false;
                } catch (InvalidMoveException ignore) {}
            }
        }
        return inStalemate;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
