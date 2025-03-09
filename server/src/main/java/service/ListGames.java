package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.HashMap;

public class ListGames {
    private AuthDAO authDAO;

    public GameDAO runListGames(GameDAO gameDAO, AuthDAO authDAO, AuthData auth)
            throws DataAccessException {
        this.authDAO = authDAO;
        verifyAuth(auth);
        return gameDAO;
    }

    private void verifyAuth(AuthData auth) throws DataAccessException {
        auth = authDAO.getAuth(auth.authToken());
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
