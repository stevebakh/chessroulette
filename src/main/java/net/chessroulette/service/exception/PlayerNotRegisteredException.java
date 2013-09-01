package net.chessroulette.service.exception;

/**
 * An exception that indicates a player requested by ID doesn't exist, or
 * more specifically, is not managed by the player Service object.
 */
public class PlayerNotRegisteredException extends Exception {
    public PlayerNotRegisteredException(String message) {
        super(message);
    }

    public PlayerNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
