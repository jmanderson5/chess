package dataaccess;

import com.google.gson.Gson;
import model.AuthData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {
    SQLFunctions calculator = new SQLFunctions();

    public SQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT authToken, json FROM authData WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuthData(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        String statement = "INSERT INTO authData (authToken, username) VALUES (?, ?)";
        var json = new Gson().toJson(authData);
        calculator.executeUpdate(statement, authData.authToken(), authData.username());
    }

    @Override
    public void deleteAuth(AuthData authData) throws DataAccessException {
        String authToken = authData.authToken();
        String statement = "DELETE FROM authData WHERE authToken=?";
        calculator.executeUpdate(statement, authToken);
    }

    @Override
    public void clearAuthData() throws DataAccessException {
        String statement = "TRUNCATE authData";
        calculator.executeUpdate(statement);
    }

    private AuthData readAuthData(ResultSet rs) throws SQLException {
        String authToken = rs.getString("authToken");
        var json = rs.getString("json");
        AuthData authData = new Gson().fromJson(json, AuthData.class);
        return authData.setAuth(authToken);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}
