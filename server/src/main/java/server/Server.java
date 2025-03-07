package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthResponse;
import model.UserData;
import service.Register;
import spark.*;

public class Server {

    public Server() {
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object register(Request req, Response res) throws DataAccessException {
        AuthResponse authResponse;
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            Register register = new Register();
            authResponse = register.runRegister(user);
        } catch (Exception e) {
            res.status(400);
            throw new DataAccessException("Error: bad request");
        }

        res.status(200);
        return new Gson().toJson(authResponse);
    }
}
