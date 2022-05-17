package bonken.game;

import bonken.utils.Action;

import java.util.ArrayList;

public interface PlayerInterface {
    /**
     *
     * @param toTrick current trick that player has to play into
     * @param minigameNum
     */
    public void requestCardToPlay(Trick toTrick, Integer minigameNum);
    public boolean isHisTurn();
    public void setHisTurn(boolean hisTurn);
    public int getScore();
    public void setScore(int score);
    public boolean getChosenPositive();
    public void setChosenPositive(boolean chosenPositive);
    public void setCardHand(CardHand cardHand);
    public CardHand getCardHand();
    public int getId();
    public String getUsername();
    public void setUsername(String username);

    /**
     *
     * @param minigames available minigames
     * @param callback onMinigameRequired
     */
    public void chooseMinigame(ArrayList<Integer> minigames, Action<Integer> callback);
    public boolean canPlay(Card card);

    /**
     * Sets the minigame chosen by the player.
     * @param minigame
     */
    public void minigameSelected(Integer minigame);

    /**
     * Sets the card played by the player.
     * @param card
     */
    public void cardSelected(String card);
}
