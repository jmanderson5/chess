package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;

public class ListGames {
    private AuthDAO authDAO;

    public GameDAO runListGames(GameDAO gameDAO, AuthDAO authDAO, String auth)
            throws DataAccessException {
        this.authDAO = authDAO;
        verifyAuth(auth);
        return gameDAO;
    }

    private void verifyAuth(String auth) throws DataAccessException {
        AuthData authData = authDAO.getAuth(auth);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
