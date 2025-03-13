package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    public GameData setGame(String gameName, String whiteUsername, String blackUsername) {
        return new GameData(this.gameID, whiteUsername, blackUsername, gameName, this.game);
    }

    public GameData setGameID(int gameID, String whiteUsername, String blackUsername) {
        return new GameData(gameID, whiteUsername, blackUsername, this.gameName, this.game);
    }
}
