package dataaccess;

import model.GameData;

import java.util.HashMap;

public class SQLGameDAO implements GameDAO {
    @Override
    public GameData getGame(String gameName) {
        return null;
    }

    @Override
    public GameData getGameByID(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<String, GameData> getGames() {
        return null;
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {

    }

    @Override
    public void clearGameData() {

    }
}
