package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public WebSocketHandler(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        MakeMoveCommand action = new Gson().fromJson(message, MakeMoveCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> connect(action.getGameID(), action.getAuthToken(), session);
            case MAKE_MOVE -> makeMove(action.getGameID(), action.getAuthToken(), session, action.getMove());
            case LEAVE -> leave(action.getGameID(), action.getAuthToken());
            case RESIGN -> resign(action.getGameID(), action.getAuthToken(), session);
        }
    }

    private void connect(Integer gameID, String authToken, Session session) throws IOException {
        GameData game;
        try {
            game = gameDAO.getGameByID(gameID);
            if (game == null) {
                throw new DataAccessException("Game does not exist");
            } else if (authDAO.getAuth(authToken) == null) {
                throw new DataAccessException("Unauthorized");
            }
            connections.add(gameID, authDAO.getAuth(authToken).username(), session);
            String message;
            String username = authDAO.getAuth(authToken).username();
            if (game.blackUsername() != null && game.blackUsername().equals(username)) {
                message = String.format("%s joined the game as BLACK", username);
            } else if (game.whiteUsername() != null && game.whiteUsername().equals(username)) {
                message = String.format("%s joined the game as WHITE", username);
            } else {
                message = String.format("%s joined the game as OBSERVER", username);
            }
            NotificationMessage notification = new NotificationMessage(message);
            connections.broadcast(gameID, authDAO.getAuth(authToken).username(), notification);
        } catch (DataAccessException | IOException e) {
            String message = String.format("Error: %s", e.getMessage());
            ErrorMessage errorNotification = new ErrorMessage(message);
            connections.directMessageError(session, errorNotification);
            return;
        }
        try {
            LoadGameMessage gameNotification = new LoadGameMessage(game);
            connections.directMessage(gameID, authDAO.getAuth(authToken).username(), gameNotification);
        } catch (DataAccessException | java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeMove(Integer gameID, String authToken, Session session, ChessMove move) throws IOException {
        GameData game;
        String username;

        try {
            // update board
            game = gameDAO.getGameByID(gameID);
            AuthData auth = authDAO.getAuth(authToken);

            if (!verifyAuth(auth, session)) {
                return;
            }
            username = auth.username();

            if (!verifyPlayerTurn(game, username, session)) {
                return;
            }

            game.game().makeMove(move);
            if (Objects.equals(game.whiteUsername(), username)) {
                GameData newGameWhite = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(),
                        game.gameName(), game.game());
                gameDAO.updateGame(newGameWhite, "whiteUsername");
            }
            if (Objects.equals(game.blackUsername(), username)) {
                GameData newGameBlack = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(),
                        game.gameName(), game.game());
                gameDAO.updateGame(newGameBlack, "blackUsername");
            }

            // send updated board
            LoadGameMessage newBoard = new LoadGameMessage(gameDAO.getGameByID(gameID));
            connections.broadcast(gameID, username, newBoard);
            connections.directMessage(gameID, username, newBoard);

            // send notification
            Integer row1 = move.getStartPosition().getRow();
            Integer col1 = move.getEndPosition().getColumn();
            Integer row2 = move.getEndPosition().getRow();
            Integer col2 = move.getEndPosition().getColumn();
            String message = String.format("%s made a move from %s,%d to %s,%d", username,
                    getColLetter(col1), row1, getColLetter(col2), row2);
            NotificationMessage notification = new NotificationMessage(message);
            connections.broadcast(gameID, username, notification);

            // game in checkmate or check?
            game = gameDAO.getGameByID(gameID);
            if (Objects.equals(game.whiteUsername(), username)) {
                if (game.game().isInCheckmate(ChessGame.TeamColor.BLACK)) { // checkmate?
                    sendCheckmate(game, gameID, username, game.blackUsername());
                } else if (game.game().isInCheck(ChessGame.TeamColor.BLACK)) { // check?
                    sendCheckMessage(gameID, username, game.blackUsername());
                }
            } else if (Objects.equals(game.blackUsername(), username)) {
                if (game.game().isInCheckmate(ChessGame.TeamColor.WHITE)) { // checkmate?
                    sendCheckmate(game, gameID, username, game.whiteUsername());
                } else if (game.game().isInCheck(ChessGame.TeamColor.WHITE)) { // check?
                    sendCheckMessage(gameID, username, game.whiteUsername());
                }
            }

        } catch (DataAccessException | InvalidMoveException | java.io.IOException e) {
            String message = String.format("Error: %s", e.getMessage());
            ErrorMessage errorNotification = new ErrorMessage(message);
            connections.directMessageError(session, errorNotification);
        }
    }

    private void sendCheckmate(GameData game, Integer gameID, String username, String userInCheckmate)
            throws DataAccessException, IOException {
        // send notification
        String message = String.format("%s is in checkmate. %s wins!", userInCheckmate, username);
        NotificationMessage notification = new NotificationMessage(message);
        connections.broadcast(gameID, username, notification);
        connections.directMessage(gameID, username, notification);
        // update game
        game.game().setGameOver(true);
        GameData newGame = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(),
                game.gameName(), game.game());
        gameDAO.updateGame(newGame, "blackUsername");
    }

    private void sendCheckMessage(Integer gameID, String username, String userInCheck)
            throws IOException, DataAccessException {
        // send notification
        String message = String.format("%s is in check", userInCheck);
        NotificationMessage notification = new NotificationMessage(message);
        connections.broadcast(gameID, username, notification);
        connections.directMessage(gameID, username, notification);
    }

    private String getColLetter(Integer numberCol) {
        return switch (numberCol) {
            case 8 -> "A";
            case 7 -> "B";
            case 6 -> "C";
            case 5 -> "D";
            case 4 -> "E";
            case 3 -> "F";
            case 2 -> "G";
            case 1 -> "H";
            default -> throw new IllegalStateException("Unexpected value: " + numberCol);
        };
    }

    private boolean verifyPlayerTurn(GameData game, String username, Session session) throws IOException {
        // verify player's turn
        ChessGame.TeamColor teamTurn = game.game().getTeamTurn();
        if (teamTurn.equals(ChessGame.TeamColor.WHITE)) {
            if (!username.equals(game.whiteUsername())) {
                String message = String.format("Error: %s", "not your turn");
                ErrorMessage errorNotification = new ErrorMessage(message);
                connections.directMessageError(session, errorNotification);
                return false;
            }
        } else {
            if (!username.equals(game.blackUsername())) {
                String message = String.format("Error: %s", "not your turn");
                ErrorMessage errorNotification = new ErrorMessage(message);
                connections.directMessageError(session, errorNotification);
                return false;
            }
        }
        return true;
    }

    private Boolean verifyAuth(AuthData auth, Session session) throws IOException {
        if (auth == null) {
            String message = String.format("Error: %s", "Unauthorized");
            ErrorMessage errorNotification = new ErrorMessage(message);
            connections.directMessageError(session, errorNotification);
            return false;
        }
        return true;
    }

    private void leave(Integer gameID, String authToken) throws DataAccessException, IOException {
        String username = authDAO.getAuth(authToken).username();
        GameData game = gameDAO.getGameByID(gameID);

        connections.remove(gameID, username);
        String message = String.format("%s left the game", username);
        NotificationMessage notification = new NotificationMessage(message);
        connections.broadcast(gameID, username, notification);

        // remove player from game
        if (Objects.equals(game.whiteUsername(), username)) {
            GameData newGameWhite = new GameData(game.gameID(), null, game.blackUsername(),
                    game.gameName(), game.game());
            gameDAO.updateGame(newGameWhite, "whiteUsername");
        }
        if (Objects.equals(game.blackUsername(), username)) {
            GameData newGameBlack = new GameData(game.gameID(), game.whiteUsername(), null,
                    game.gameName(), game.game());
            gameDAO.updateGame(newGameBlack, "blackUsername");
        }
    }

    private void resign(Integer gameID, String authToken, Session session) throws DataAccessException, IOException {
        String username = authDAO.getAuth(authToken).username();
        GameData game = gameDAO.getGameByID(gameID);
        boolean isGameOver = game.game().isGameOver();

        if (!verifyPlayer(game, username, session)) { return; }
        if (isGameOver) {
            String message = "Error: game is over. cannot resign";
            ErrorMessage errorNotification = new ErrorMessage(message);
            connections.directMessageError(session, errorNotification);
            return;
        }

        // set gameOver
        game.game().setGameOver(true);

        // update game
        GameData newGame = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(),
                game.gameName(), game.game());
        gameDAO.updateGame(newGame, "whiteUsername");
        gameDAO.updateGame(newGame, "blackUsername");

        String message = String.format("%s resigned", username);
        NotificationMessage notification = new NotificationMessage(message);
        connections.directMessage(gameID, username, notification);
        connections.broadcast(gameID, username, notification);
    }

    private boolean verifyPlayer(GameData game, String username, Session session) throws IOException {
        // verify user is a player
        if (username.equals(game.whiteUsername()) || username.equals(game.blackUsername())) {
            return true;
        } else {
            String message = String.format("Error: you, %s, are not a player. cannot resign", username);
            ErrorMessage errorNotification = new ErrorMessage(message);
            connections.directMessageError(session, errorNotification);
            return false;
        }
    }
}
