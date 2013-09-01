package net.chessroulette.model;

import org.empyrn.darkknight.gamelogic.MoveGen;
import org.empyrn.darkknight.gamelogic.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Game class represents an active game between two
 * connected participants. The game logic is handled by
 * the DarkKnight library, wrapped up by this class.
 */
public class Game {
    /**
     * A unique identifier for the game.
     */
    private final String id = UUID.randomUUID().toString();

    /**
     * A key->value map of the two players participating in the game.
     */
    private final Map<String, Player> players = new HashMap<>(2);

    /**
     * An instance of the current turn (with legal moves + identifier)
     */
    private Turn currentTurn;

    /**
     * A new instance of the object that handles the game logic.
     */
    private final org.empyrn.darkknight.gamelogic.Game game =
            new org.empyrn.darkknight.gamelogic.Game(null, 0, 0, 0);

    /**
     * A single, static instance of the move generator object that can calculate legal moves.
     */
    private static final MoveGen moveGenerator = new MoveGen();

    /**
     * Construct a new Game object that represents a new, active game.
     *
     * @param whitePlayer A player controlling the white pieces.
     * @param blackPlayer A player controlling the black pieces.
     */
    public Game(final Player whitePlayer, final Player blackPlayer) {
        game.setPlayerNames(whitePlayer.getId(), blackPlayer.getId());
        players.put(whitePlayer.getId(), whitePlayer);
        players.put(blackPlayer.getId(), blackPlayer);
        currentTurn = new Turn(getPlayerToMove(), getLegalMoves());
    }

    /**
     * Get the unique identifier for the game.
     *
     * @return The unique identifier for the game.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the players participating in this game.
     *
     * @return A collection containing the participating players in the game.
     */
    public Collection<Player> getPlayers() {
        return players.values();
    }

    /**
     * Get the player for the current turn of the game.
     *
     * @return The player for the current turn of the game.
     */
    public Player getPlayerToMove() {
        return players.get(game.getCurrentTurnPlayerName());
    }

    /**
     * Get an object representing the current turn of the game.
     *
     * @return The current turn of the game.
     */
    public Turn getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Get a list of legal moves that can be made by the player of the current turn.
     *
     * @return A list of legal moves for the current turn.
     */
    public List<Move> getLegalMoves() {
        final Position position = game.getPosition();

        // Argh, darkknight doesn't use the List<> interface!
        ArrayList<org.empyrn.darkknight.gamelogic.Move> dkMoves;
        dkMoves = MoveGen.removeIllegal(position,
                moveGenerator.pseudoLegalMoves(position));

        final List<Move> moves = new ArrayList<>(dkMoves.size());

        for (org.empyrn.darkknight.gamelogic.Move m : dkMoves) {
            moves.add(new Move(m.to, m.from));
        }

        return moves;
    }
}
