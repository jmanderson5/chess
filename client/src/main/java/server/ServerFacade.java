package server;

import exception.ResponseException;
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
            websocketCommunicator = new WebsocketCommunicator(serverUrl);
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
        // connect to WebSocket
        websocketCommunicator = new WebsocketCommunicator(serverUrl);
        websocketCommunicator.setAuthToken(authToken);
        websocketCommunicator.connect(gameID);
    }

    public void observeGame(String gameID) {

    }

    public void makeMove(String userColor, Integer gameID) {

    }
}
