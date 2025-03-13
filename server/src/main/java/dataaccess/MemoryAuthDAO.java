package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {
    private HashMap<String, AuthData> users = new HashMap<>();

    public MemoryAuthDAO() throws DataAccessException {
    }

    @Override
    public AuthData getAuth(String authToken) {
        return users.get(authToken);
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        users.put(authData.authToken(), authData);
    }

    @Override
    public void deleteAuth(AuthData authData) {
        users.remove(authData.authToken());
    }

    @Override
    public void clearAuthData() {
        users = new HashMap<>();
    }
}
