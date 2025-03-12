package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameDAOTest {

    private GameDAO gameDAO = new SQLGameDAO();

    public GameDAOTest() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        gameDAO.clearGameData();
    }

    @Test
    void getGameTest() {

    }
 }
