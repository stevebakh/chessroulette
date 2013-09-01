package net.chessroulette.event;

import net.chessroulette.model.Player;

public class PlayerRegistered extends AbstractPlayerEvent {
    /**
     * Construct the event passing in a reference to the player
     * that has registered with the web application.
     *
     * @param player The player that has been registered.
     */
    public PlayerRegistered(final Player player) {
        super(player);
    }
}
