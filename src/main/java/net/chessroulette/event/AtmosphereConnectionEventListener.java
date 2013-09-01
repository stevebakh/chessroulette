package net.chessroulette.event;

import net.chessroulette.service.PlayerService;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;

/**
 *
 *
 *
 */
public class AtmosphereConnectionEventListener
        extends AtmosphereResourceEventListenerAdapter {
    /**
     * A reference to the service object that manages players.
     */
    private final PlayerService playerService;

    /**
     * Construct the event listener, passing in the service object
     * used to manage players on the system.
     *
     * @param playerService The service object for managing players.
     */
    public AtmosphereConnectionEventListener(final PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Once the connection has successfully been suspended, register
     * the player with the PlayerService.
     *
     * @param event {@inheritDoc}
     */
    @Override
    public void onSuspend(final AtmosphereResourceEvent event) {
        super.onSuspend(event);

        // Use the atmosphere resource UUID as the player ID
        playerService.registerNewPlayer(event.getResource().uuid());
    }

    /**
     * When the user disconnects, deregister them as player with the
     * PlayerService.
     *
     * @param event {@inheritDoc}
     */
    @Override
    public void onDisconnect(final AtmosphereResourceEvent event) {
        super.onDisconnect(event);

        // Use the atmosphere resource UUID as the player ID
        playerService.deregisterPlayer(event.getResource().uuid());
    }
}
