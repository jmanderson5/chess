package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class Register {
    UserDAO userDAO;
    AuthDAO authDAO;

    public UserData getUser(String username) throws DataAccessException {
        return userDAO.getUser(username);
    }

    public void createUser(UserData user) throws DataAccessException {
        userDAO.createUser(user);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void createAuth(String username) throws DataAccessException {
        authDAO.createAuth(new AuthData(generateToken(), username));
    }
}
