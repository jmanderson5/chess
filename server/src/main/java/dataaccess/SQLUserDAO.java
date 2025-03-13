package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
        };
        calculator.configureDatabase(createStatements);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT username, json FROM userData WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUserData(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private UserData readUserData(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        var json = rs.getString("json");
        UserData userData = new Gson().fromJson(json, UserData.class);
        return userData.setUser(username);
    }

    @Override
    public String createUser(UserData user) throws DataAccessException {
        String statement = "INSERT INTO userData (username, password, email, json) VALUES (?, ?, ?, ?)";
        var json = new Gson().toJson(user);
        calculator.executeUpdate(statement, user.username(), user.password(), user.email(), json);
        return user.username();
    }

    @Override
    public void clearUserData() throws DataAccessException {
        String statement = "TRUNCATE userData";
        calculator.executeUpdate(statement);
    }
}
