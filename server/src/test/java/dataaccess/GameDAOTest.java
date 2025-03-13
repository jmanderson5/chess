package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameDAOTest {

    private GameDAO gameDAO = new SQLGameDAO();

    public GameDAOTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        gameDAO.clearGameData();
    }

    @Test
    void getGameTest() throws DataAccessException {
        GameData gameData = new GameData(0, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);
        assertDoesNotThrow(() -> gameDAO.getGameByID(gameData.gameID()));
    }
 }
