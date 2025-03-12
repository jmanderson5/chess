package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTest {

    private UserDAO userDAO = new SQLUserDAO();

    public UserDAOTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        userDAO.clearUserData();
    }

    @Test
    void getUserTest() {

    }
}
