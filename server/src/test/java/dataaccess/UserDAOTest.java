package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDAOTest {

    private UserDAO userDAO = new SQLUserDAO();

    public UserDAOTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        userDAO.clearUserData();
    }

    @Test
    void getUserTest() throws DataAccessException {
        UserData userData = new UserData("jmander", "password", "jmander@byu.edu");
        userDAO.createUser(userData);
        assertDoesNotThrow(() -> userDAO.getUser(userData.username()));
    }

    @Test
    void getUserFail() throws DataAccessException {
        UserData userData = new UserData("bill", "password", "littleMan");
        userDAO.createUser(userData);

        assertNotEquals(userData, userDAO.getUser("bob"));
    }

    @Test
    void createUserTest() {
        UserData userData = new UserData("jmander", "password", "jmander@byu.edu");
        assertDoesNotThrow(() -> userDAO.createUser(userData));
    }

    @Test
    void createUserFail() throws DataAccessException {
        UserData userData = new UserData("jmander", "password", "jmander@byu.edu");
        UserData wrongUser = new UserData("bill", "password", "billEmail");
        assertDoesNotThrow(() -> userDAO.createUser(wrongUser));

        assertNotEquals(userData, userDAO.getUser(wrongUser.username()));
    }

    @Test
    void clearUserTest() throws DataAccessException {
        UserData userData = new UserData("jmander", "password", "jmander@byu.edu");
        userDAO.createUser(userData);
        assertDoesNotThrow(() -> userDAO.clearUserData());
    }
}
