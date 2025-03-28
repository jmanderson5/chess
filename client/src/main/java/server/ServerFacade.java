package server;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.UserData;
import model.handler.AuthResponse;
import model.handler.GameResult;
import model.handler.Games;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class ServerFacade {

    private final String serverUrl;
    private String authToken;

    public ServerFacade(String url) {
        this.serverUrl = url;
    }

    public AuthResponse register(UserData user) throws ResponseException {
        var path = "/user";
        AuthResponse authResponse = this.makeRequest("POST", path, user, AuthResponse.class);
        authToken = authResponse.authToken();
        return authResponse;
    }

    public AuthResponse login(UserData user) throws ResponseException {
        var path = "/session";
        AuthResponse authResponse = this.makeRequest("POST", path, user, AuthResponse.class);
        authToken = authResponse.authToken();
        return authResponse;
    }

    public void logout() throws ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, null);
    }

    public Games listGames() throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, null, Games.class);
    }

    public GameResult createGame(String game) throws ResponseException {
        String path = "/game";
        record GameName(String gameName) {}
        return this.makeRequest("POST", path, new GameName(game), GameResult.class);
    }

    public void joinGame() throws ResponseException {
        var path = "/game";
        this.makeRequest("PUT", path, null, Games.class);
    }

    public void clear() throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass)
            throws ResponseException{
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.addRequestProperty("authorization", authToken);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr);
                }
            }

            throw new ResponseException(status, "other failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
