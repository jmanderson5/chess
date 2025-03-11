package dataaccess;

import model.UserData;

public class SQLUserDAO implements UserDAO {
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
