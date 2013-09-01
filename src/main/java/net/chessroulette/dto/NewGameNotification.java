package net.chessroulette.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NewGameNotification {
    /**
     * The unique identifier for the game.
     */
    @XmlElement(required = true)
    private String gameId;

    /**
     *
     *
     * @return
     */
    public String getGameId() {
        return gameId;
    }

    /**
     *
     *
     * @param gameId
     */
    public void setGameId(final String gameId) {
        this.gameId = gameId;
    }
}
