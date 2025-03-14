package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

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
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);

        assertDoesNotThrow(() -> gameDAO.getGameByID(gameData.gameID()));
    }

    @Test
    void getGameFail() throws DataAccessException {
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);
        GameData newGame = gameDAO.getGame("smith");

        assertNotEquals(gameData, newGame);
    }

    @Test
    void getGameIDTest() throws DataAccessException {
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);

        assertDoesNotThrow(() -> gameDAO.getGame(gameData.gameName()));
    }

    @Test
    void getGameIDFail() throws DataAccessException {
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);
        GameData newGame = gameDAO.getGameByID(2);

        assertNotEquals(gameData, newGame);
    }

    @Test
    void getGamesTest() throws DataAccessException {
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);

        assertDoesNotThrow(() -> gameDAO.getGames());
    }

    @Test
    void getGamesFail() throws DataAccessException {
        HashMap<String, GameData> games = new HashMap<>();
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        games.put(gameData.gameName(), gameData);

        assertNotEquals(games, gameDAO.getGames());
    }

    @Test
    void createGameTest() throws DataAccessException {
        GameData gameData = new GameData(1, "jmander", null, "gameName",
                new ChessGame());

        assertDoesNotThrow(() -> gameDAO.createGame(gameData));
        assertEquals(gameData, gameDAO.getGame("gameName"));
        assertEquals(gameData, gameDAO.getGameByID(1));
    }

    @Test
    void createGameFail() throws DataAccessException {
        GameData gameData = new GameData(1, "jmander", null, "gameName",
                new ChessGame());
        GameData wrongGame = new GameData(2, "jmander", null, "wrongGame",
                new ChessGame());

        assertDoesNotThrow(() -> gameDAO.createGame(wrongGame));
        assertNotEquals(gameData, gameDAO.getGame("wrongGame"));
        assertNotEquals(gameData, gameDAO.getGameByID(2));
    }

    @Test
    void updateGameTest() throws DataAccessException {
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);
        GameData newGameData = new GameData(1, "jmander", null,
                "gameName", gameData.game());

        assertDoesNotThrow(() -> gameDAO.updateGame(newGameData, "whiteUsername"));
    }

    @Test
    void updateGameFail() throws DataAccessException {
        GameData gameData = new GameData(1, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);
        GameData newGameData = new GameData(1, "jmander", null,
                "gameName", gameData.game());

        assertThrows(DataAccessException.class, () -> gameDAO.updateGame(newGameData, "bill cosby"));
    }

    @Test
    void clearGameDataTest() throws DataAccessException {
        GameData gameData = new GameData(0, null, null, "gameName",
                new ChessGame());
        gameDAO.createGame(gameData);
        HashMap<String, GameData> expected = new HashMap<>();

        assertDoesNotThrow(() -> gameDAO.clearGameData());
        assertEquals(expected, gameDAO.getGames());
    }
 }
