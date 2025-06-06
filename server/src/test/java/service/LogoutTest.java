package service;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest {
    UserDAO userDAO = new SQLUserDAO();
    AuthDAO authDAO = new SQLAuthDAO();
    Logout service = new Logout();

    public LogoutTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        userDAO.clearUserData();
        authDAO.clearAuthData();
    }

    @Test
    void runLogoutValid() throws DataAccessException {
        AuthData auth = new AuthData("1234", "jmander");
        authDAO.createAuth(auth);
        service.runLogout(authDAO, "1234");

        assertNull(authDAO.getAuth("1234"));
    }

    @Test
    void runLoginInvalid() throws DataAccessException {
        AuthData auth = new AuthData("1234", "jmander");
        authDAO.createAuth(auth);
        service.runLogout(authDAO, "1234");

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runLogout(authDAO, "1234");
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
