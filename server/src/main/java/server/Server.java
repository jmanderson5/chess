package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.Register;
import spark.*;

public class Server {
    private Register register;

    public Server() {
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void register(Request req) throws DataAccessException {
        var registration = new Gson().fromJson(req.body(), Register.class);
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}
