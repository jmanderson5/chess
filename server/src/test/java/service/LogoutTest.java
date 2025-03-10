package service;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest {
    static final Logout service = new Logout();
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

    @BeforeEach
    void setup() {
        userDAO.clearUserData();
        authDAO.clearAuthData();
    }

    @Test
    void runLogoutValid() throws DataAccessException {
        AuthData auth = new AuthData("1234", "jmander");
        authDAO.createAuth(auth);
        service.runLogout(authDAO, auth);

        assertNull(authDAO.getAuth("1234"));
    }

    @Test
    void runLoginInvalid() throws DataAccessException {
        AuthData auth = new AuthData("1234", "jmander");
        authDAO.createAuth(auth);
        AuthData wrongAuth = new AuthData("1234", "jmander");
        service.runLogout(authDAO, wrongAuth);

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runLogout(authDAO, auth);
        });

        assertEquals("Error: unauthorized", exception.getMessage());
    }
}
