package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.handler.GameDataShort;
import model.handler.Games;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListGamesTest {
    GameDAO gameDAO = new SQLGameDAO();
    AuthDAO authDAO = new SQLAuthDAO();
    ListGames service = new ListGames();

    public ListGamesTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        gameDAO.clearGameData();
        authDAO.clearAuthData();
    }

    @Test
    void listGamesValid() throws DataAccessException {
        authDAO.createAuth(new AuthData("1234", "jmander"));
        gameDAO.createGame(new GameData(2345, "", "",
                "compitition", new ChessGame()));
        Games gamesList = service.runListGames(gameDAO, authDAO, "1234");
        List<GameDataShort> listExample = new ArrayList<>();
        listExample.add(new GameDataShort(2345, "", "", "compitition"));
        Games gamesExample = new Games(listExample);

        assertEquals(gamesExample, gamesList);
    }

    @Test
    void listGamesInvalid() {
        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runListGames(gameDAO, authDAO, "1234");
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
