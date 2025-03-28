package client;

import chess.ChessGame;
import exception.ResponseException;
import model.GameData;
import model.UserData;
import model.handler.AuthResponse;
import model.handler.GameDataShort;
import model.handler.GameResult;
import model.handler.Games;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() throws ResponseException {
        server = new Server();
        int port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() throws ResponseException {
        server.stop();
    }


    @Test
    public void registerTest() throws ResponseException {
        serverFacade.clear();
        String username = "username";
        String password = "password";
        String email = "email";

        AuthResponse auth = serverFacade.register(new UserData(username, password, email));

        assertEquals(username, auth.username());
    }

    @Test
    public void registerFailed() throws ResponseException {
        serverFacade.clear();
        String username = "username";
        String password = "password";
        String email = "email";

        serverFacade.register(new UserData(username, password, email));

        Exception exception = assertThrows(ResponseException.class, () -> {
            serverFacade.register(new UserData(username, password, email));
        });

        assertEquals("Error: already taken", exception.getMessage());
    }

    @Test
    public void loginTest() throws ResponseException {
        serverFacade.clear();
        String username = "username";
        String password = "password";
        String email = "email";

        serverFacade.register(new UserData(username, password, email));
        serverFacade.logout();
        AuthResponse auth = serverFacade.login(new UserData(username, password, email));

        assertEquals(username, auth.username());
    }

    @Test
    public void loginFail() throws ResponseException {
        serverFacade.clear();
        String username = "username";
        String password = "password";
        String email = "email";

        Exception exception = assertThrows(ResponseException.class, () -> {
            serverFacade.login(new UserData(username, password, email));
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void logoutTest() throws ResponseException {
        serverFacade.clear();
        String username = "username";
        String password = "password";
        String email = "email";

        serverFacade.register(new UserData(username, password, email));
        serverFacade.logout();
    }

    @Test
    public void logoutFail() throws ResponseException {
        serverFacade.clear();
        String username = "username";
        String password = "password";
        String email = "email";

        serverFacade.register(new UserData(username, password, email));
        serverFacade.logout();

        Exception exception = assertThrows(ResponseException.class, () -> {
            serverFacade.logout();
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void listGamesTest() throws ResponseException {
        serverFacade.clear();
        serverFacade.register(new UserData("username", "password", "email"));
        List<GameDataShort> gamesList = new ArrayList<>();

        GameResult gameResult = serverFacade.createGame("game");
        GameResult gameResult2 = serverFacade.createGame("name");
        GameDataShort game = new GameDataShort(gameResult.gameID(), null, null,
                "game");
        GameDataShort game2 = new GameDataShort(gameResult2.gameID(), null, null,
                "name");
        gamesList.add(game);
        gamesList.add(game2);
        Games games = new Games(gamesList);

        assertEquals(games, serverFacade.listGames());
    }

    @Test
    public void listGamesFail() throws ResponseException {
        serverFacade.clear();

        Exception exception = assertThrows(ResponseException.class, () -> {
            serverFacade.listGames();
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void createGameTest() throws ResponseException {
        serverFacade.clear();
        serverFacade.register(new UserData("username", "password", "email"));

        serverFacade.createGame("game");
        Games gamesRecord = serverFacade.listGames();
        List<GameDataShort> games = gamesRecord.games();
        GameDataShort gameData = null;
        for (GameDataShort game : games) {
            gameData = game;
        }

        assert gameData != null;
        assertEquals(new GameDataShort(gameData.gameID(), null, null, "game"),
                gameData);
    }

    @Test
    public void createGameFail() throws ResponseException {
        serverFacade.clear();

        Exception exception = assertThrows(ResponseException.class, () -> {
            serverFacade.createGame("game");
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void joinGameTest() throws ResponseException {
        serverFacade.clear();
        serverFacade.register(new UserData("username", "password", "email"));

        GameResult gameResult = serverFacade.createGame("game");
        serverFacade.joinGame("WHITE", gameResult.gameID());

        Games gamesRecord = serverFacade.listGames();
        List<GameDataShort> games = gamesRecord.games();
        GameDataShort gameData = null;
        for (GameDataShort game : games) {
            gameData = game;
        }

        assert gameData != null;
        assertEquals(new GameDataShort(gameData.gameID(), "username", null, "game"),
                gameData);
    }

    @Test void joinGameFail() throws ResponseException {
        serverFacade.clear();

        Exception exception = assertThrows(ResponseException.class, () -> {
            serverFacade.joinGame("username", 1234);
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
