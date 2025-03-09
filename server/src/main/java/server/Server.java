package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.AuthData;
import model.AuthResponse;
import model.LoginData;
import model.UserData;
import service.*;
import spark.*;

public class Server {

    private UserDAO userDAO = new MemoryUserDAO();
    private AuthDAO authDAO = new MemoryAuthDAO();
    private GameDAO gameDAO = new MemoryGameDAO();

    public Server() {
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.delete("/db", this::clear);
        Spark.exception(DataAccessException.class, this::exceptionHandler);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        String message = ex.getMessage();
        if (message.equals("Error: already taken")) {
            res.status(403);
            res.body(new Gson().toJson(message));
        } else if (message.equals("Error: unauthorized")) {
            res.status(401);
            res.body(new Gson().toJson(message));
        } else if (message.equals("Error: bad request")) {
            res.status(400);
            res.body(new Gson().toJson(message));
        }
    }

    private Object register(Request req, Response res) throws DataAccessException {
        AuthResponse authResponse;
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            Register register = new Register();
            authResponse = register.runRegister(userDAO, authDAO, user);
        } catch (Exception e) {
            throw new DataAccessException("Error: already taken");
        }

        res.status(200);
        return new Gson().toJson(authResponse);
    }

    private Object login(Request req, Response res) throws DataAccessException {
        AuthResponse authResponse;
        try {
            LoginData user = new Gson().fromJson(req.body(), LoginData.class);
            Login login = new Login();
            authResponse = login.runLogin(userDAO, authDAO, user);
        } catch (Exception e) {
            throw new DataAccessException("Error: unauthorized");
        }

        res.status(200);
        return new Gson().toJson(authResponse);
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        AuthData auth = new Gson().fromJson(req.body(), AuthData.class);
        Logout logout = new Logout();
        try {
            logout.runLogout(authDAO, auth);
        } catch (Exception e) {
            throw new DataAccessException("Error: unauthorized");
        }

        res.status(200);
        return new Gson().toJson(auth);
    }

    private Object listGames(Request req, Response res) throws  DataAccessException {
        AuthData auth = new Gson().fromJson(req.body(), AuthData.class);
        ListGames listGames = new ListGames();
        try {
            res.status(200);
            return new Gson().toJson(listGames.runListGames(gameDAO, authDAO, auth));
        } catch (Exception e) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    private Object clear(Request req, Response res) {
        Clear clear = new Clear(userDAO, authDAO, gameDAO);

        res.status(200);
        return new Gson().toJson(clear);
    }
}
