package dataaccess;

import model.AuthData;

public interface AuthDAO {
    AuthData getAuth(String authToken);

    void createAuth(AuthData authData) throws DataAccessException;

    void deleteAuth(AuthData authData);

    void clearAuthData();
}
