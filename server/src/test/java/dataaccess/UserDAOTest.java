package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    void createUserTest() {
        UserData userData = new UserData("jmander", "password", "jmander@byu.edu");
        assertDoesNotThrow(() -> userDAO.createUser(userData));
    }

    @Test
    void clearUserTest() throws DataAccessException {
        UserData userData = new UserData("jmander", "password", "jmander@byu.edu");
        userDAO.createUser(userData);
        assertDoesNotThrow(() -> userDAO.clearUserData());
    }
}
