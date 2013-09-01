package net.chessroulette.model;

/**
 *
 *
 *
 */
public class Player {
    /**
     * The unique identifier for the player.
     */
    private final String id;

    /**
     * A reference to the game the player is currently participating in.
     */
    private Game game;

    /**
     * Construct a new Player object that represents a connected user.
     *
     * @param id A unique identifier for the player.
     */
    public Player(final String id) {
        this.id = id;
    }

    /**
     * Get the unique identifier for the player.
     *
     * @return The unique identifier for the player.
     */
    public String getId() {
        return id;
    }

    /**
     * Associate a chess game with the player.
     *
     * @param game A chess game that the player is about to participate in.
     */
    public void setGame(final Game game) {
        this.game = game;
    }

    /**
     * Override the toString method to output the Player's ID.
     *
     * @return the player's ID.
     */
    @Override
    public String toString() {
        return "Player{id='" + id + "'}";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;

        if (!id.equals(player.id)) {
            return false;
        }

        return true;
    }
}
