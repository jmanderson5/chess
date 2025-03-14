package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AuthDAOTest {

    private AuthDAO authDAO = new SQLAuthDAO();

    public AuthDAOTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        authDAO.clearAuthData();
    }

    @Test
    void getAuthTest() throws DataAccessException {
        AuthData authData = new AuthData("1234", "jmander");
        authDAO.createAuth(authData);
        assertDoesNotThrow(() -> authDAO.getAuth(authData.username()));
    }

    @Test
    void createAuthTest() {
        AuthData authData = new AuthData("1234", "jmander");
        assertDoesNotThrow(() -> authDAO.createAuth(authData));
    }

    @Test
    void deleteAuthTest() throws DataAccessException {
        List<AuthData> expected = new ArrayList<>();

        AuthData deleteAuth = new AuthData("0", "joe");
        AuthData auth1 = new AuthData("1", "sally");
        AuthData auth2 = new AuthData("2", "fido");

        authDAO.createAuth(deleteAuth);
        authDAO.createAuth(auth1);
        authDAO.createAuth(auth2);

        expected.add(auth1);
        expected.add(auth2);

        assertDoesNotThrow(() -> authDAO.deleteAuth(deleteAuth));
    }

    @Test
    void clearAuthTest() throws DataAccessException {
        authDAO.createAuth(new AuthData("1", "sally"));

        assertDoesNotThrow(() -> authDAO.clearAuthData());
    }
}
