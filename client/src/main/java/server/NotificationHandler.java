package server;

import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

public interface NotificationHandler {
    public void loadGame(LoadGameMessage serverMessage);
    public void error(ErrorMessage message);
    public void notification(NotificationMessage message);
}
