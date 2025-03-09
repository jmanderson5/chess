package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {
    private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public GameData getGame(Integer gameID) {
        return games.get(gameID);
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {
        games.put(gameData.gameID(), gameData);
    }

    @Override
    public void clearGameData() {
        games = new HashMap<>();
    }
}
