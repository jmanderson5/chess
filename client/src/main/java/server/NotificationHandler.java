package server;

import websocket.messages.LoadGameMessage;

public interface NotificationHandler {
    public void loadGame(LoadGameMessage serverMessage);
    public void error();
    public void notification();
}
