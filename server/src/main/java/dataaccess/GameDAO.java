package dataaccess;

import model.GameData;

import java.util.HashMap;

public interface GameDAO {
    GameData getGame(String gameName) throws DataAccessException;

    GameData getGameByID(int gameID) throws DataAccessException;

    HashMap<String, GameData> getGames() throws DataAccessException;

    void createGame(GameData gameData) throws DataAccessException;

    void clearGameData() throws DataAccessException;
}
