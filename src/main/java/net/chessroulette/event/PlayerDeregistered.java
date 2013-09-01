package net.chessroulette.event;

import net.chessroulette.model.Player;

/**
 *
 */
public class PlayerDeregistered extends AbstractPlayerEvent {
    /**
     * Construct the event passing in a reference to the player
     * that has been deregistered from the web application.
     *
     * @param player The player that has been deregistered.
     */
    public PlayerDeregistered(final Player player) {
        super(player);
    }
}
