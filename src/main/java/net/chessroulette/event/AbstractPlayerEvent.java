package net.chessroulette.event;

import net.chessroulette.model.Player;

/**
 *
 *
 */
abstract public class AbstractPlayerEvent {
    /**
     * The player to associate with the event.
     */
    private final Player player;

    /**
     * Construct the event passing in a reference to the player
     * that is the focus of the event.
     *
     * @param player The player that is the focus or source of the event.
     */
    public AbstractPlayerEvent(final Player player) {
        this.player = player;
    }

    /**
     * Get the Player object associated with the event.
     *
     * @return The player associated with the event.
     */
    public Player getPlayer() {
        return player;
    }
}
