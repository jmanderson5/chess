package service;

import dataaccess.*;
import model.AuthData;
import model.handler.AuthResponse;
import model.UserData;

import java.util.UUID;

public class Register {
    UserDAO userDAO;
    AuthDAO authDAO;

    public AuthResponse runRegister(UserDAO userDAO, AuthDAO authDAO, UserData user) throws DataAccessException {
        this.userDAO = userDAO;
        this.authDAO = authDAO;

        UserData newUser = userDAO.getUser(user.username());
        if (newUser != null) { throw new DataAccessException("Error: already taken"); }
        if (user.username() == null || user.password() == null || user.email() == null ||
                user.username().isEmpty() || user.password().isEmpty() || user.email().isEmpty()) {
            throw new DataAccessException("Error: bad request");
        }
        String username = createUser(user);
        String authData = createAuth(username);

        return new AuthResponse(username, authData);
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
