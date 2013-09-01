package net.chessroulette.service;

import net.chessroulette.event.PlayerDeregistered;
import net.chessroulette.event.PlayerRegistered;
import net.chessroulette.model.Player;
import net.chessroulette.service.exception.PlayerNotRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * EJB service layer class used to manage registered chess players.
 * This includes creating/registering new players, tracking players
 * awaiting opponents to play, etc.
 */
@Stateless
public class PlayerService {
    /**
     * Implementation of SLF4J logging interface.
     */
    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    /**
     * CDI event interface to fire events when a player is registered.
     */
    @Inject
    private Event<PlayerRegistered> playerRegisteredEvent;

    /**
     * CDI event interface to fire events when a player is deregistered.
     */
    @Inject
    private Event<PlayerDeregistered> playerDeregisteredEvent;

    /**
     * An aggregation of all registered players.
     */
    private Map<String, Player> players = new HashMap<>();

    /**
     * Collection of players waiting for an opponent. This should typically
     * contain 1 player at most, but may expand due to race conditions, etc.
     */
    private static final Queue<Player> waitingPlayers = new ArrayDeque<>();

    /**
     * Retrieve an opponent from the queue of waiting players.
     *
     * @return A player waiting for an opponent.
     */
    public Player getOpponent() {
        log.debug("Requesting opponent. Number of players in wait queue: {}", waitingPlayers.size());

        return waitingPlayers.poll();
    }

    /**
     * Add a player to a queue while waiting for another player
     * to connect and play.
     *
     * @param player The player to add to the queue.
     */
    public void addPlayerToQueue(final Player player) {
        log.debug("Adding {} to the wait queue.", player);

        waitingPlayers.add(player);
    }

    /**
     * Create and register a new player.
     * The unique identifier for the player is passed in. This is
     * likely to be the UUID of the atmosphere resource associated
     * with the user's connection, but it could be anything.
     *
     * @param playerId The unique identifier to use for the player.
     * @return An object representing the newly connected Player.
     */
    public Player registerNewPlayer(final String playerId) {
        log.debug("Register new player with id '{}'", playerId);

        final Player player = new Player(playerId);
        players.put(player.getId(), player);
        playerRegisteredEvent.fire(new PlayerRegistered(player));
        return player;
    }

    /**
     * Deregister a user from the system by ID. Remove the player from
     * the map of players and also from the waiting queue, if they are there.
     *
     * @param playerId The unique identifier of the player to deregister.
     */
    public void deregisterPlayer(final String playerId) {
        log.debug("Deregister player with id '{}'", playerId);

        final Player player = players.remove(playerId);
        waitingPlayers.remove(player);
        playerDeregisteredEvent.fire(new PlayerDeregistered(player));
    }

    /**
     * Get a previously registered player by ID.
     *
     * @param playerId The unique identifier for the player.
     * @return The player matching the specified ID.
     * @throws PlayerNotRegisteredException
     */
    public Player getPlayer(final String playerId) throws PlayerNotRegisteredException {
        log.debug("Get player with id '{}'", playerId);

        if (!players.containsKey(playerId)) {
            log.error("Unable to retrieve player with id '{}'", playerId);

            throw new PlayerNotRegisteredException(
                    "Player with id '" + playerId + "' not registered with service.");
        }

        return players.get(playerId);
    }
}
