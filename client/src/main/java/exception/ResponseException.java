package exception;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseException extends Exception {

    final private int statusCode;

    public ResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public static ResponseException fromJson(InputStream stream) {
        JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        int status = 500;
        if (message.equals("Error: already taken")) {
            status = 403;
        } else if (message.equals("Error: unauthorized")) {
            status = 401;
        } else if (message.equals("Error: bad request")) {
            status = 400;
        }
        return new ResponseException(status, message);
    }
}
