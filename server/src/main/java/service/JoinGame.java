package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;

public class JoinGame {
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();

    public void runJoinGame(AuthDAO authDAO, GameDAO gameDAO, String auth, String playerColor,
                            int gameID) throws DataAccessException {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        String username = verifyAuth(auth);
        joinGame(username, playerColor, gameID);
    }

    private String verifyAuth(String auth) throws DataAccessException {
        AuthData authData = authDAO.getAuth(auth);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return authData.username();
    }

    private void joinGame(String username, String playerColor, int gameID) throws DataAccessException {
        GameData game = gameDAO.getGameByID(gameID);
        if (playerColor.equals("WHITE")) {
            gameDAO.createGame(new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game()));
        } else {
            gameDAO.createGame(new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game()));
        }
    }
}
