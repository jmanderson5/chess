package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;

public class MemoryAuthDAO implements AuthDAO {
    private List<AuthData> users = new ArrayList<>();

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        users.add(authData);
    }

    @Override
    public void clearAuthData() {
        users = new ArrayList<>();
    }
}
