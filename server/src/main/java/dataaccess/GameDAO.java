package dataaccess;

import model.GameData;

import java.util.HashMap;

public interface GameDAO {
    GameData getGame(Integer gameID);

    HashMap<Integer, GameData> getGames();

    void createGame(GameData gameData) throws DataAccessException;

    void clearGameData();
}
