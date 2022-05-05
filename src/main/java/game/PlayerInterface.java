package game;

public interface PlayerInterface {
    public Card play(Card card);
    public boolean isHisTurn();
    public void setHisTurn(boolean hisTurn);
    public int getScore();
    public void setScore(int score);
    public void setCardHand(CardHand cardHand);
    public int getId();

}
