package bonken.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class CardHandTest {

    private final Card aH = new Card(Card.ACE, Card.HEARTS);
    private final Card qS = new Card(Card.QUEEN, Card.SPADES);
    private final Card tH = new Card(2, Card.HEARTS);
    private final Card fC = new Card(5, Card.CLUBS);
    private final Card sD = new Card(7, Card.DIAMONDS);
    private final Card qH = new Card(Card.QUEEN, Card.HEARTS);
    private final ArrayList<Card> cards = new ArrayList<>(List.of(aH, qS, tH, fC, sD));
    private final CardHand cardHand = new CardHand(cards);

    @Test
    void addAllButHeartsTest() {
        ArrayList<Card> expected = new ArrayList<>(List.of(qS, fC, sD));
        ArrayList<Card> testResult = cardHand.addAllButHearts();
        assertEquals(expected, testResult);
    }

    @Test
    void hasThisSuitTest() {
        ArrayList<Card> expected = new ArrayList<>(List.of(aH, tH));
        ArrayList<Card> testResult = cardHand.hasThisSuit(qH);
        assertEquals(expected, testResult);
    }

}
