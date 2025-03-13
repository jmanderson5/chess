package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class ClearTest {
    UserDAO userDAO = new SQLUserDAO();
    AuthDAO authDAO = new SQLAuthDAO();
    GameDAO gameDAO = new SQLGameDAO();

    public ClearTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        userDAO.clearUserData();
        authDAO.clearAuthData();
        gameDAO.clearGameData();
    }

    @Test
    void clearTest() throws DataAccessException {
        UserData user = new UserData("jmander", "happy", "jmander@byu.edu");
        AuthData auth = new AuthData("1234", "jmander");
        GameData game = new GameData(1234, "jmander", "bob",
                "challengers", new ChessGame());

        try {
            userDAO.createUser(user);
            authDAO.createAuth(auth);
            gameDAO.createGame(game);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        new Clear(userDAO, authDAO, gameDAO);

        assertNull(userDAO.getUser("jmander"));
        assertNull(authDAO.getAuth("1234"));
        assertNull(gameDAO.getGame("challengers"));
    }
}
