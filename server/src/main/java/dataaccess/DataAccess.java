package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

public interface DataAccess {
    void clear();
    UserData createUser();
    UserData getUser(String userName);
    GameData createGame();
    GameData getGame(String gameID);
    GameData listGames();
    GameData updateGame(ChessGame game);
    AuthData createAuth();
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken);
}
