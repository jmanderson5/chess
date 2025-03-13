package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    public GameData setGame(String gameName) {
        return new GameData(this.gameID, this.whiteUsername, this.blackUsername, gameName, this.game);
    }

    public GameData setGameID(int gameID) {
        return new GameData(gameID, this.whiteUsername, this.blackUsername, this.gameName, this.game);
    }
}
