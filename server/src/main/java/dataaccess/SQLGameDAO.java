package dataaccess;

import com.google.gson.Gson;
import model.GameData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SQLGameDAO implements GameDAO {
    SQLFunctions calculator = new SQLFunctions();

    public SQLGameDAO() throws DataAccessException {
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS gameData (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
        };
        calculator.configureDatabase(createStatements);
    }

    @Override
    public GameData getGame(String gameName) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT gameName, json, blackUsername, whiteUsername FROM gameData WHERE gameName=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGameData(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private GameData readGameData(ResultSet rs) throws SQLException {
        String gameName = rs.getString("gameName");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        var json = rs.getString("json");
        GameData gameData = new Gson().fromJson(json, GameData.class);
        return gameData.setGame(gameName, whiteUsername, blackUsername);
    }

    @Override
    public GameData getGameByID(int gameID) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String statement = "SELECT gameID, blackUsername, whiteUsername, json FROM gameData WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGameDataID(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private GameData readGameDataID(ResultSet rs) throws SQLException {
        int gameID = rs.getInt("gameID");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        var json = rs.getString("json");
        GameData gameData = new Gson().fromJson(json, GameData.class);
        return gameData.setGameID(gameID, whiteUsername, blackUsername);
    }

    @Override
    public HashMap<String, GameData> getGames() throws DataAccessException {
        HashMap<String, GameData> result = new HashMap<>();
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM gameData";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.put(readGameData(rs).gameName(), readGameData(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {
        String statement = "INSERT INTO gameData (gameID, whiteUsername, blackUsername, gameName, json) " +
                "VALUES (?, ?, ?, ?, ?)";
        var json = new Gson().toJson(gameData);
        calculator.executeUpdate(statement, gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(),
                gameData.gameName(), json);
    }

    @Override
    public void clearGameData() throws DataAccessException {
        String statement = "TRUNCATE gameData";
        calculator.executeUpdate(statement);
    }

    @Override
    public void updateGame(GameData gameData, String userColor) throws DataAccessException {
        if (userColor.equals("whiteUsername")) {
            String statement;
            if (gameData.whiteUsername() != null) {
                statement = "UPDATE gameData SET whiteUsername = '" + gameData.whiteUsername() +
                        "' WHERE gameID = " + gameData.gameID();
            } else {
                statement = "UPDATE gameData SET whiteUsername = NULL WHERE gameID = " + gameData.gameID();
            }
            calculator.executeUpdate(statement);
        } else if (userColor.equals("blackUsername")) {
            String statement;
            if (gameData.blackUsername() != null) {
                statement = "UPDATE gameData SET blackUsername = '" + gameData.blackUsername() +
                        "' WHERE gameID = " + gameData.gameID();
            } else {
                statement = "UPDATE gameData SET blackUsername = NULL WHERE gameID = " + gameData.gameID();
            }
            calculator.executeUpdate(statement);
        } else {
            throw new DataAccessException("Error: bad request");
        }

        String statementJson = "UPDATE gameData SET json = '" + new Gson().toJson(gameData) +
                "' WHERE gameID = " + gameData.gameID();
        calculator.executeUpdate(statementJson);
    }
}
