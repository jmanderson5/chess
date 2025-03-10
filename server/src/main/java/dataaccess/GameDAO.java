package dataaccess;

import model.GameData;

import java.util.HashMap;

public interface GameDAO {
    GameData getGame(String gameName);

    GameData getGameByID(int gameID) throws DataAccessException;

    HashMap<String, GameData> getGames();

    void createGame(GameData gameData) throws DataAccessException;

    void clearGameData();
}
