package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AuthTest {

    private AuthDAO authDAO = new SQLAuthDAO();

    public AuthTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        authDAO.clearAuthData();
    }

    @ParameterizedTest
    @ValueSource(classes = {SQLAuthDAO.class, MemoryAuthDAO.class})
    void createAuthTest(Class<? extends AuthDAO> dbClass) throws DataAccessException {

        AuthData authData = new AuthData("1234", "jmander");
        assertDoesNotThrow(() -> authDAO.createAuth(authData));
    }
}
