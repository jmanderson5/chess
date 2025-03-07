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

    public String createUser(UserData user) throws DataAccessException {
        return userDAO.createUser(user);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String createAuth(String username) throws DataAccessException {
        String authToken = generateToken();
        authDAO.createAuth(new AuthData(authToken, username));
        return authToken;
    }
}
