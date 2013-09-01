package net.chessroulette.model;

import java.util.List;
import java.util.UUID;

/**
 *
 */
public class Turn {
    /**
     *
     */
    private final String id = UUID.randomUUID().toString();

    /**
     *
     */
    private final Player playerToMove;

    /**
     *
     */
    private final List<Move> legalMoves;

    /**
     *
     *
     * @param playerToMove
     * @param legalMoves
     */
    public Turn(final Player playerToMove, final List<Move> legalMoves) {
        this.playerToMove = playerToMove;
        this.legalMoves = legalMoves;
    }

    /**
     *
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     *
     * @return
     */
    public Player getPlayerToMove() {
        return playerToMove;
    }

    /**
     *
     *
     * @return
     */
    public List<Move> getLegalMoves() {
        return legalMoves;
    }
}
