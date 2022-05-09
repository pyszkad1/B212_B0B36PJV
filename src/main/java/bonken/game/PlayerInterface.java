package bonken.game;

import java.util.ArrayList;

public interface PlayerInterface {
    public Card play(Card card);
    public boolean isHisTurn();
    public void setHisTurn(boolean hisTurn);
    public int getScore();
    public void setScore(int score);
    public void setCardHand(CardHand cardHand);
    public CardHand getCardHand();
    public int getId();
    public Integer chooseMinigame(ArrayList<Integer> minigames);

}
