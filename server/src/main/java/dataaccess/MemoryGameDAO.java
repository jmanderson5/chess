package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {
    private HashMap<String, GameData> games = new HashMap<>();

    @Override
    public GameData getGame(String gameName) {
        return games.get(gameName);
    }

    @Override
    public GameData getGameByID(int gameID) throws DataAccessException {
        for (GameData game : games.values()) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        throw new DataAccessException("Error: bad request");
    }

    @Override
    public HashMap<String, GameData> getGames() {
        return games;
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {
        games.put(gameData.gameName(), gameData);
    }

    @Override
    public void clearGameData() {
        games = new HashMap<>();
    }
}
