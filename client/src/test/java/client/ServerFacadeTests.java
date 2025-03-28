package client;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import exception.ResponseException;
import model.UserData;
import model.handler.AuthResponse;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
}
