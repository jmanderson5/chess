package service;

import dataaccess.*;
import model.AuthData;
import model.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGameTest {
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();
    CreateGame service = new CreateGame();

    @BeforeEach
    void setup() {
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
        assertEquals("", gameDAO.getGame(gameName).whiteUsername());
        assertEquals("", gameDAO.getGame(gameName).blackUsername());
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
    void notAuthorized() throws DataAccessException {
        AuthData auth = new AuthData("1234", "jmander");
        String gameName = "awesome sauce";

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runCreateGame(authDAO, gameDAO, "1234", gameName);
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
