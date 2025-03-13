package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.handler.AuthResponse;
import model.handler.LoginData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class Login {
    UserDAO userDAO;
    AuthDAO authDAO;

    public AuthResponse runLogin(UserDAO userDAO, AuthDAO authDAO, LoginData user) throws DataAccessException {
        this.userDAO = userDAO;
        this.authDAO = authDAO;

        UserData loginUser = userDAO.getUser(user.username());
        if (loginUser == null || !loginUser.username().equals(user.username()) ||
//                !loginUser.password().equals(user.password())) {
                  !BCrypt.checkpw(user.password(), loginUser.password())) {
            throw new DataAccessException("Error: unauthorized");
        }

        String authToken = createAuth(user.username());

        return new AuthResponse(user.username(), authToken);
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
