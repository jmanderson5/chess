package service;

import dataaccess.*;

public class Clear {

    public Clear(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) throws DataAccessException {
        userDAO.clearUserData();
        authDAO.clearAuthData();
        gameDAO.clearGameData();
    }
}
