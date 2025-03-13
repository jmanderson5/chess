package dataaccess;

import model.UserData;

public class SQLUserDAO implements UserDAO {
    SQLFunctions calculator = new SQLFunctions();

    public SQLUserDAO() throws DataAccessException {
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS  userData (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
        };
        calculator.configureDatabase(createStatements);
    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public String createUser(UserData user) throws DataAccessException {
        return "";
    }

    @Override
    public void clearUserData() {

    }
}
