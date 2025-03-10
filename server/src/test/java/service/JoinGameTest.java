package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JoinGameTest {
    private AuthDAO authDAO = new MemoryAuthDAO();
    private GameDAO gameDAO = new MemoryGameDAO();
    private JoinGame service = new JoinGame();

    @BeforeEach
    void setup() {
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
    }

    @Test
    void joinGameValid() throws DataAccessException {
        ChessGame game = new ChessGame();
        authDAO.createAuth(new AuthData("1234", "jmander"));
        gameDAO.createGame(new GameData(1234, "", "",
                "gameName", game));
        service.runJoinGame(authDAO, gameDAO, "1234", "WHITE", 1234);
        GameData referenceGame = new GameData(1234, "jmander", "",
                "gameName", game);

        assertEquals(referenceGame, gameDAO.getGameByID(1234));
    }

    @Test
    void notUnauthorized() throws DataAccessException {
        ChessGame game = new ChessGame();
        gameDAO.createGame(new GameData(1234, "", "",
                "gameName", game));

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runJoinGame(authDAO, gameDAO, "1234", "WHITE", 1234);
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
