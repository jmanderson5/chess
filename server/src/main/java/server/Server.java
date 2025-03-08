package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.AuthResponse;
import model.UserData;
import service.Register;
import spark.*;

public class Server {

    private UserDAO userDAO = new MemoryUserDAO();
    private AuthDAO authDAO = new MemoryAuthDAO();

    public Server() {
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
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
        }
    }

    private Object register(Request req, Response res) throws DataAccessException {
        AuthResponse authResponse;
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            Register register = new Register();
            authResponse = register.runRegister(userDAO, authDAO, user);
        } catch (Exception e) {
            res.status(400);
            throw new DataAccessException("Error: already taken");
        }

        res.status(200);
        return new Gson().toJson(authResponse);
    }

    private Object clear(Request req, Response res) {
        return null;
    }
}
