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
    void runRegisterValid() throws DataAccessException {
        UserData user = new UserData("jmander", "happy", "jmander@byu.edu");
        AuthResponse data = service.runRegister(user);

        assertEquals(data.username(), "jmander");
        assertNotNull(data.authToken());
        assertTrue(data.authToken().matches(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }

    @Test
    void runRegisterInvalid() throws DataAccessException {
        UserData user = new UserData("jmander", "happy", "jmander@byu.edu");
        service.runRegister(user);

        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.runRegister(user);
        });

        assertEquals("Error: already taken", exception.getMessage());
    }
}
