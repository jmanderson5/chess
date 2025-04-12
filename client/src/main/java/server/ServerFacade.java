package server;

import chess.ChessMove;
import exception.ResponseException;
import model.GameData;
import model.UserData;
import model.handler.*;

public class ServerFacade {

    private final String serverUrl;
    private String authToken;
    private HttpCommunicator httpCommunicator;
    private WebsocketCommunicator websocketCommunicator;

    public ServerFacade(String url) {
        this.serverUrl = url;
        httpCommunicator = new HttpCommunicator(serverUrl);
        try {
            websocketCommunicator = new WebsocketCommunicator(serverUrl, websocketCommunicator);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthResponse register(UserData user) throws ResponseException {
        var path = "/user";
        AuthResponse authResponse = httpCommunicator.makeRequest("POST", path, user, AuthResponse.class);
        authToken = authResponse.authToken();
        httpCommunicator.setAuthToken(authToken);
        return authResponse;
    }

    public AuthResponse login(LoginData user) throws ResponseException {
        var path = "/session";
        AuthResponse authResponse = httpCommunicator.makeRequest("POST", path, user, AuthResponse.class);
        authToken = authResponse.authToken();
        httpCommunicator.setAuthToken(authToken);
        return authResponse;
    }

    public void logout() throws ResponseException {
        var path = "/session";
        httpCommunicator.makeRequest("DELETE", path, null, null);
    }

    public Games listGames() throws ResponseException {
        var path = "/game";
        return httpCommunicator.makeRequest("GET", path, null, Games.class);
    }

    public GameResult createGame(String game) throws ResponseException {
        String path = "/game";
        record GameName(String gameName) {}
        return httpCommunicator.makeRequest("POST", path, new GameName(game), GameResult.class);
    }

    public void clear() throws ResponseException {
        var path = "/db";
        httpCommunicator.makeRequest("DELETE", path, null, null);
        authToken = null;
    }

    // WebSocket stuff
    public void joinGame(String userColor, Integer gameID) throws ResponseException {
        var path = "/game";
        httpCommunicator.makeRequest("PUT", path, new JoinGameData(userColor, gameID), null);
        var dataPath = "/data";
        // connect to WebSocket
        websocketCommunicator.setAuthToken(authToken);
        websocketCommunicator.connect(gameID, userColor);
    }

    public void observeGame(Integer gameID) throws ResponseException {
        websocketCommunicator.setAuthToken(authToken);
        websocketCommunicator.connect(gameID, "WHITE");
    }

    public void redraw(Integer gameID, String playerColor) throws ResponseException {
        websocketCommunicator.connect(gameID, playerColor);
    }

    public void leave(Integer gameID) throws ResponseException {
        websocketCommunicator.disconnect(gameID);
    }

    public void makeMove(Integer gameID, ChessMove move) throws ResponseException {
        websocketCommunicator.makeMove(gameID, move);
    }
}
