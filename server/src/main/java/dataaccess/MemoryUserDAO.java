package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    private HashMap<String, UserData> users = new HashMap<>();

    @Override
    public UserData getUser(String username){
        return users.get(username);
    }

    @Override
    public String createUser(UserData user) throws DataAccessException {
        users.put(user.username(), user);
        return user.username();
    }

    @Override
    public void clearUserData() {
        this.users = new HashMap<>();
    }
}
