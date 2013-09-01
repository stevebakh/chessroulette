package net.chessroulette.event;

import net.chessroulette.model.Game;

/**
 *
 */
public class GameCreated {
    /**
     *
     */
    private final Game game;

    /**
     *
     * @param game
     */
    public GameCreated(Game game) {
        this.game = game;
    }

    /**
     *
     * @return
     */
    public Game getGame() {
        return game;
    }
}
