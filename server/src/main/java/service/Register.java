package service;

import dataaccess.*;
import model.AuthData;
import model.AuthResponse;
import model.UserData;

import java.util.UUID;

public class Register {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

    public AuthResponse runRegister(UserData user) throws DataAccessException {
        UserData newUser = userDAO.getUser(user.username());
        if (newUser != null) { throw new DataAccessException("Error: already taken"); }
        String username = createUser(user);
        String authData = createAuth(username);
        return new AuthResponse(username, authData);
    }

    private UserData getUser(String username) throws DataAccessException {
        return userDAO.getUser(username);
    }

    private String createUser(UserData user) throws DataAccessException {
        return userDAO.createUser(user);
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String createAuth(String username) throws DataAccessException {
        String authToken = generateToken();
        authDAO.createAuth(new AuthData(authToken, username));
        return authToken;
    }
}
