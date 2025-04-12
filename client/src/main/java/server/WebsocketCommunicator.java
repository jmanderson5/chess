package server;

import chess.ChessBoard;
import com.google.gson.Gson;
import exception.ResponseException;
import ui.Board;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketCommunicator extends Endpoint {

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
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case LOAD_GAME -> loadGame(new Gson().fromJson(message, LoadGameMessage.class));
                        case ERROR -> error();
                        case NOTIFICATION -> notification();
                    }
                }
            });

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            System.out.println("Websocket Communicator Error");
        }
    }

    public void connect(Integer gameID) throws ResponseException {
        try {
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void loadGame(LoadGameMessage serverMessage) {
        Board board = new Board();
        ChessBoard game = serverMessage.getGame().game().getBoard();
        board.drawBoard(game, "WHITE");
        System.out.print("[IN GAME] >>> ");
    }

    private void error() {

    }

    private void notification() {

    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }
}
