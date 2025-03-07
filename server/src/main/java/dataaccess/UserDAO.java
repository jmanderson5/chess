package dataaccess;

import model.UserData;

public interface UserDAO {
    UserData getUser(String username) throws DataAccessException;

    String createUser(UserData user) throws DataAccessException;
}
