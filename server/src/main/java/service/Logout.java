package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

public class Logout {
    AuthDAO authDAO;

    public void runLogout(AuthDAO authDAO, String auth) throws DataAccessException {
        this.authDAO = authDAO;

        AuthData authData = authDAO.getAuth(auth);
        if (authData == null) { throw new DataAccessException("Error: unauthorized"); }
        authDAO.deleteAuth(authData);
    }
}
