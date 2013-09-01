package net.chessroulette.model;

/**
 *
 */
public class Move {
    /**
     *
     */
    private final Integer to;

    /**
     *
     */
    private final Integer from;

    /**
     *
     *
     * @param to
     * @param from
     */
    public Move(final Integer to, final Integer from) {
        this.to = to;
        this.from = from;
    }

    /**
     *
     *
     * @return
     */
    public Integer getTo() {
        return to;
    }

    /**
     *
     *
     * @return
     */
    public Integer getFrom() {
        return from;
    }
}
