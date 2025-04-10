package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<String, Connection>> connections =
            new ConcurrentHashMap<>();

    public void add(Integer gameID, String username, Session session) {
        Connection connection = new Connection(username, session);
        try {
            connections.get(gameID).put(username, connection);
        } catch (Exception e) {
            ConcurrentHashMap<String, Connection> newConnection = new ConcurrentHashMap<>();
            newConnection.put(username, connection);
            connections.put(gameID, newConnection);
        }
    }

    public void remove(Integer gameID, String username) {
        connections.get(gameID).remove(username);
    }

    public void broadcast(Integer gameID, String excludeVisitorName, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        ConcurrentHashMap<String, Connection> value = connections.get(gameID);

        for (Connection connection : value.values()) {
            if (connection.session.isOpen()) {
                if (!connection.visitorName.equals(excludeVisitorName)) {
                    connection.send(new Gson().toJson(notification));
                }
            } else {
                removeList.add(connection);
            }
        }

        // Clean up any connections that were left open.
        for (Connection connection : removeList) {
            connections.get(gameID).remove(connection.visitorName);
        }
    }

    public void directMessage(Integer gameID, String username, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        ConcurrentHashMap<String, Connection> value = connections.get(gameID);

        for (Connection connection : value.values()) {
            if (connection.session.isOpen()) {
                if (connection.visitorName.equals(username)) {
                    connection.send(new Gson().toJson(notification));
                }
            } else {
                removeList.add(connection);
            }
        }

        // Clean up any connections that were left open.
        for (Connection connection : removeList) {
            connections.get(gameID).remove(connection.visitorName);
        }
    }
}
