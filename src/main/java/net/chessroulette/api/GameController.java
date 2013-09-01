package net.chessroulette.api;

import net.chessroulette.dto.Move;
import net.chessroulette.dto.NewGameNotification;
import net.chessroulette.event.AtmosphereConnectionEventListener;
import net.chessroulette.event.GameCreated;
import net.chessroulette.event.PlayerRegistered;
import net.chessroulette.model.Player;
import net.chessroulette.service.GameService;
import net.chessroulette.service.PlayerService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.jersey.SuspendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 *
 *
 */
@Path("game")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameController {
    /**
     * Implementation of SLF4J logging interface.
     */
    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    /**
     * EJB service to manage connected players.
     */
    @EJB
    private PlayerService playerService;

    /**
     * EJB service to manage active games.
     */
    @EJB
    private GameService gameService;

    /**
     * Instance of the JERSEY broadcaster factory for creating/looking up broadcasters.
     */
    @Context
    private BroadcasterFactory broadcasterFactory;

    /**
     * Suspend the connection between the client and server, holding
     * it open for information to be sent back to the client.
     *
     * @param atmosphereResource Represents a user's connection to the application.
     * @return Response that suspends the connection.
     */
    @GET
    @Path("connect")
    public SuspendResponse<String> connect(@Context final AtmosphereResource atmosphereResource) {
        log.debug("New connection request received.");

        // Return a suspense response, suspending the connection, setting a broadcaster to
        // allow communication directly with the individual user, and attaching an atmosphere
        // connection event listener that registers/deregisters the user as a player.
        return new SuspendResponse.SuspendResponseBuilder<String>()
                .writeEntity(false)
                .outputComments(true)
                .broadcaster(broadcasterFactory.get("/players/" + atmosphereResource.uuid()))
                .addListener(new AtmosphereConnectionEventListener(playerService))
                .build();
    }

    /**
     * Take a turn, moving a piece on the board. The movement of a piece
     * must correspond with a specific turn, which is identified using the
     * unique turnId token and the player's ID (extrapolated from the
     * atmosphereResource).
     *
     * @param atmosphereResource Represents a user's connection to the application.
     * @param gameId A unique token representing game the move belongs to.
     * @param turnId A unique token representing this specific turn in the game.
     * @param move The DTO representing the movement of the player's piece.
     * @return An object representing the appropriate HTTP response.
     */
    @POST
    @Path("move/{gameId}/{turnId}")
    public Response move(
            @Context final AtmosphereResource atmosphereResource,
            @PathParam("gameId") final String gameId,
            @PathParam("turnId") final String turnId,
            final Move move) {

        final String playerId = atmosphereResource.uuid();

        return Response.ok().build();
    }

    /**
     * When a new player is registered, check if there's an opponent available
     * to start a new game, placing the newly registered player onto the wait
     * queue if there aren't any players waiting for a game.
     *
     * @param playerRegisteredEvent An event representing the newly registered player.
     */
    public void playerRegistered(@Observes final PlayerRegistered playerRegisteredEvent) {
        log.debug("Handling new player registered event.");

        final Player player1 = playerRegisteredEvent.getPlayer();
        final Player player2 = playerService.getOpponent();

        if (player2 == null) {
            playerService.addPlayerToQueue(player1);
        } else {
            final List<Player> players = Arrays.asList(player1, player2);
            Collections.shuffle(players);
            gameService.createNewGame(players.get(0), players.get(1));
        }
    }

    /**
     * Notify the players that a new game has begun.
     *
     * @param gameCreatedEvent An event representing the newly created game.
     */
    public void gameCreated(@Observes final GameCreated gameCreatedEvent) {
        log.debug("Handling new game created event.");

        // Alert both players that a game has been started.
        for (final Player player : gameCreatedEvent.getGame().getPlayers()) {
            final Broadcaster broadcaster =
                    broadcasterFactory.lookup("/players/" + player.getId());

            final NewGameNotification notification = new NewGameNotification();
            notification.setGameId(gameCreatedEvent.getGame().getId());
            broadcaster.broadcast(notification);
        }
    }
}
