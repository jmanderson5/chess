package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.GameDataShort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListGames {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private final List<GameDataShort> gamesList = new ArrayList<>();

    public List<GameDataShort> runListGames(GameDAO gameDAO, AuthDAO authDAO, String auth)
            throws DataAccessException {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        verifyAuth(auth);
        createGamesList();

        return gamesList;
    }

    private void verifyAuth(String auth) throws DataAccessException {
        AuthData authData = authDAO.getAuth(auth);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    private void createGamesList() {
        HashMap<String, GameData> games = gameDAO.getGames();

        for (GameData value : games.values()) {
            GameDataShort game = new GameDataShort(value.gameID(), value.whiteUsername(),
                    value.blackUsername(), value.gameName());
            gamesList.add(game);
        }
    }
}
