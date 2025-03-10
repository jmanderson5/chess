package service;

import dataaccess.*;
import model.handler.AuthResponse;
import model.handler.LoginData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    static final Login service = new Login();
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

    @BeforeEach
    void setup() {
        userDAO.clearUserData();
        authDAO.clearAuthData();
    }

    @Test
    void runLoginValid() throws DataAccessException {
        UserData user = new UserData("jmander", "happy", "jmander@byu.edu");
        userDAO.createUser(user);
        LoginData userLogin = new LoginData("jmander", "happy");
        AuthResponse data = service.runLogin(userDAO, authDAO, userLogin);

        assertEquals(data.username(), "jmander");
        assertNotNull(data.authToken());
        assertTrue(data.authToken().matches(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }

    @Test
    void runLoginInvalid() throws DataAccessException {
        UserData user = new UserData("jmander", "happy", "jmander@byu.edu");
        userDAO.createUser(user);
        LoginData userLogin = new LoginData("jmander", "wrongPassword");

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runLogin(userDAO, authDAO, userLogin);
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
