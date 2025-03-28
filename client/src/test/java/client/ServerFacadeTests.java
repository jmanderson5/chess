package client;

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
        serverFacade = new ServerFacade("http://localhost:" + port);
        System.out.println("Started test HTTP server on " + port);
        serverFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerTest() throws ResponseException {
        String username = "username";
        String password = "password";
        String email = "email";

        AuthResponse auth = serverFacade.register(new UserData(username, password, email));

        assertEquals(username, auth.username());
    }

    @Test
    public void registerFailed() throws ResponseException {
        String username = "username";
        String password = "password";
        String email = "email";

        AuthResponse auth = serverFacade.register(new UserData(username, password, email));

        assertEquals(username, auth.username());

        Exception exception = assertThrows(ResponseException.class, () -> {
            serverFacade.register(new UserData(username, password, email));
        });

        assertEquals("Error: already taken", exception.getMessage());
    }

}
