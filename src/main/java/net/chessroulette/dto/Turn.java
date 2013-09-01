package net.chessroulette.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO representing information about the current turn, including the
 * legal moves available and an identifier used as a token to make sure no
 * external actor other than the intended player can interfere with the game.
 */
@XmlRootElement
@XmlSeeAlso({Move.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class Turn {
    /**
     * The unique identifier for this specific turn
     */
    @XmlElement(required = true)
    private String id;

    /**
     * The legal moves that can be taken this turn.
     */
    @XmlElement(required = true)
    private List<Move> legalMoves = new ArrayList<>();
}
