package service;

import dataaccess.DataAccessException;
import model.AuthResponse;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    static final Register service = new Register();

//    @BeforeEach
//    void clear() throws ResponseException {
//        service.clear();
//    }

    @Test
    void runRegister() throws DataAccessException {
        UserData user = new UserData("jmander", "happy", "jmander@byu.edu");
        AuthResponse data = service.runRegister(user);

        assertEquals(data.username(), "jmander");
        assertNotNull(data.authToken());
        assertTrue(data.authToken().matches("[a-zA-Z0-9]{10,}"));
    }
}
