package service;

import dataaccess.*;
import model.AuthData;
import model.handler.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGameTest {
    AuthDAO authDAO = new SQLAuthDAO();
    GameDAO gameDAO = new SQLGameDAO();
    CreateGame service = new CreateGame();

    public CreateGameTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        authDAO.clearAuthData();
        gameDAO.clearGameData();
    }

    @Test
    void runCreateGameValid() throws DataAccessException {
        AuthData auth = new AuthData("1234", "jmander");
        authDAO.createAuth(auth);
        String gameName = "awesome sauce";
        GameResult result = service.runCreateGame(authDAO, gameDAO, "1234", gameName);

        assertTrue(result.gameID() >= 1000 && result.gameID() <= 9999);
        assertEquals("awesome sauce", gameDAO.getGame(gameName).gameName());
        assertEquals(null, gameDAO.getGame(gameName).whiteUsername());
        assertEquals(null, gameDAO.getGame(gameName).blackUsername());
    }

    @Test
    void duplicateGame() throws DataAccessException {
        AuthData auth = new AuthData("1234", "jmander");
        authDAO.createAuth(auth);
        String gameName = "awesome sauce";
        service.runCreateGame(authDAO, gameDAO, "1234", gameName);

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runCreateGame(authDAO, gameDAO, "1234", gameName);
        });

        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    void notAuthorized() {
        String gameName = "awesome sauce";

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runCreateGame(authDAO, gameDAO, "1234", gameName);
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
