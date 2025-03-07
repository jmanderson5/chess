package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;

public class MemoryAuthDAO implements AuthDAO {
    final private List<AuthData> users = new ArrayList<>();

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        users.add(authData);
    }
}
