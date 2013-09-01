package net.chessroulette.service;

import net.chessroulette.event.GameCreated;
import net.chessroulette.model.Game;
import net.chessroulette.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * EJB service layer class used to manage active games.
 */
@Stateless
public class GameService {
    /**
     * Implementation of SLF4J logging interface.
     */
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    /**
     * CDI event interface to fire events when a new game is created.
     */
    @Inject
    private Event<GameCreated> gameCreatedEvent;

    /**
     * An aggregation of all active games.
     */
    private final Map<String, Game> games = new HashMap<>();

    /**
     * Create a new game of Chess between two players. Fires a {@link GameCreated}
     * CDI event upon creation, containing the game instance.
     *
     * @param whitePlayer The player representing the white side.
     * @param blackPlayer The player representing the black side.
     * @return The newly created chess game.
     */
    public Game createNewGame(final Player whitePlayer, final Player blackPlayer) {
        log.info("Request to create new game with players: {} and {}", whitePlayer, blackPlayer);

        Game game = new Game(whitePlayer, blackPlayer);
        games.put(game.getId(), game);

        whitePlayer.setGame(game);
        blackPlayer.setGame(game);

        gameCreatedEvent.fire(new GameCreated(game));
        return game;
    }
}
