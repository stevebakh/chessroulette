package net.chessroulette.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A DTO representing a player's movement of a piece on the board.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Move {
    /**
     * The original position of the piece that has moved.
     */
    @XmlElement(required = true)
    private Integer from;

    /**
     * The destination of the piece that has moved.
     */
    @XmlElement(required = true)
    private Integer to;
}
