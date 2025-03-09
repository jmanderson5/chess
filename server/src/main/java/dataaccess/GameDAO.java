package dataaccess;

import model.GameData;

public interface GameDAO {
    GameData getGame(Integer gameID);

    void createGame(GameData gameData) throws DataAccessException;

    void clearGameData();
}
