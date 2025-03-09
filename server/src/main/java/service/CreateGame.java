package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.GameResult;

import java.util.Random;

public class CreateGame {
    AuthDAO authDAO;
    GameDAO gameDAO;

    public GameResult runCreateGame(AuthDAO authDAO, GameDAO gameDAO, AuthData auth,
                                    String gameName) throws DataAccessException {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        verifyAuth(auth);
        verifyGameNullity(gameName);

        return createGame(gameName);
    }

    private void verifyAuth(AuthData auth) throws DataAccessException {
        auth = authDAO.getAuth(auth.authToken());
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    private void verifyGameNullity(String gameName) throws DataAccessException {
        GameData game = gameDAO.getGame(gameName);
        if (game != null) {
            throw new DataAccessException(("Error: bad request"));
        }
    }

    private GameResult createGame(String gameName) throws DataAccessException {
        Random random = new Random();
        int gameID = 1000 + random.nextInt(9000);
        GameData game = new GameData(gameID, "", "", gameName, new ChessGame());
        gameDAO.createGame(game);

        return new GameResult(game.gameID());
    }
}
