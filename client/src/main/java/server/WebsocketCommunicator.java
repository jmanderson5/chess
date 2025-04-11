package server;

import com.google.gson.Gson;
import exception.ResponseException;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static org.glassfish.grizzly.Interceptor.RESET;

public class WebsocketCommunicator {

    Session session;
    String authToken;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public WebsocketCommunicator(String url) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    notifyUsers(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void notifyUsers(ServerMessage notification) {
        System.out.print(RED);
        System.out.println(notification.getServerMessageType());
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }

    public void connect(Integer gameID) throws ResponseException {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
