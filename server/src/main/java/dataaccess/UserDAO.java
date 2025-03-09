package dataaccess;

import model.UserData;

public interface UserDAO {
    UserData getUser(String username);

    String createUser(UserData user) throws DataAccessException;

    void clearUserData();
}
