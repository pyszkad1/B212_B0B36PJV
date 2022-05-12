package bonken.game;

import bonken.utils.Action;

import java.util.ArrayList;

public abstract class Player implements PlayerInterface {
    String username;
    final int id;
    protected CardHand cardHand;
    ArrayList<Card> playableCards;
    protected int score;
    protected boolean hisTurn;
    protected boolean chosenPositive;
    protected Trick trickToPlayTo;

    private Position pos;

    public Player(int id, Position pos) {
        this.pos = pos;
        this.username = "no username";
        this.id = id;
        chosenPositive = false;
        this.playableCards = new ArrayList<>();
    }

    @Override
    public abstract void chooseMinigame(ArrayList<Integer> minigames, Action<Integer> callback) ;

    protected abstract void getCardToPlay();

    @Override
    public boolean isHisTurn() {
        return hisTurn;
    }

    @Override
    public void setHisTurn(boolean hisTurn) {
        this.hisTurn = hisTurn;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void setCardHand(CardHand cardHand) {
        this.cardHand = cardHand;
    }

    public CardHand getCardHand() {
        return cardHand;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public void requestCardToPlay(Trick trick) {

        trickToPlayTo = trick;

        Card card = trick.cards[0] ;

        if(card == null) card = new Card(2, -1);

        playableCards = cardHand.getPlayableCards(card);

        getCardToPlay();
    }

    protected void putCard(Card playedCard) {

        if(this.canPlay(playedCard) == false) return;

        this.playableCards.clear();
        this.cardHand.hand.remove(playedCard);

        Trick tmp = trickToPlayTo;
        trickToPlayTo = null;
        tmp.addCard(this, playedCard);
    }

    public boolean canPlay(Card card) {
        return this.playableCards.contains(card);
    }

    public Position getPos() {
        return pos;
    }
}
