package bonken.game;

import bonken.utils.Action;

import java.util.ArrayList;

public interface PlayerInterface {
    public void requestCardToPlay(Trick toTrick, Integer minigameNum);
    public boolean isHisTurn();
    public void setHisTurn(boolean hisTurn);
    public int getScore();
    public void setScore(int score);
    public void setCardHand(CardHand cardHand);
    public CardHand getCardHand();
    public int getId();
    public String getUsername();
    public void chooseMinigame(ArrayList<Integer> minigames, Action<Integer> callback);
    public boolean canPlay(Card card);

}
