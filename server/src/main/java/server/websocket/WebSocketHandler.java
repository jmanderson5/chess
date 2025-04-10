package server.websocket;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private GameDAO gameDAO;
    private AuthDAO authDAO;

    public WebSocketHandler(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> connect(action.getGameID(), action.getAuthToken(), session);
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave(action.getGameID(), action.getAuthToken());
            case RESIGN -> resign();
        }
    }

    private void connect(Integer gameID, String authToken, Session session) throws DataAccessException, IOException {
        GameData game;
        try {
            game = gameDAO.getGameByID(gameID);
            if (game == null) {
                throw new DataAccessException("Game does not exist");
            } else if (authDAO.getAuth(authToken) == null) {
                throw new DataAccessException("Unauthorized");
            }
            connections.add(gameID, authDAO.getAuth(authToken).username(), session);
            String message = String.format("%s join the game as %s", authDAO.getAuth(authToken).username(), authToken);
            NotificationMessage notification = new NotificationMessage(message);
            connections.broadcast(gameID, authDAO.getAuth(authToken).username(), notification);
        } catch (DataAccessException | IOException e) {
            String message = String.format("Error: %s", e.getMessage());
            ErrorMessage errorNotification = new ErrorMessage(message);
            connections.directMessageError(session, errorNotification);
            return;
        }
        try {
            LoadGameMessage gameNotification = new LoadGameMessage(game.game().getBoard());
            connections.directMessage(gameID, authDAO.getAuth(authToken).username(), gameNotification);
        } catch (DataAccessException | java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeMove() {}

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

    private void resign() {}
}
