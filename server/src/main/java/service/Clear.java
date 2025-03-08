package service;

import dataaccess.*;

public class Clear {

    public void runClear(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        userDAO.clearUserData();
        authDAO.clearAuthData();
        gameDAO.clearGameData();
    }
}
