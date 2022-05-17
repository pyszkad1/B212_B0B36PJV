package bonken.game;

import bonken.utils.Action;
import bonken.utils.DoubleAction;

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
    DoubleAction<Trick, Integer> giveServerTrickEnd;

    private Position pos;

    /**
     * Player constructor for net game.
     * @param id
     * @param pos
     * @param giveServerTrickEnd called when the whole trick is finished
     */
    public Player(int id, Position pos, DoubleAction<Trick, Integer> giveServerTrickEnd) {
        this.pos = pos;
        this.username = "no username";
        this.id = id;
        chosenPositive = false;
        this.playableCards = new ArrayList<>();
        this.giveServerTrickEnd = giveServerTrickEnd;
    }

    /**
     * Player constructor for offline game.
     * @param id
     * @param pos
     */
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

    public boolean getChosenPositive() {
        return chosenPositive;
    }

    public void setChosenPositive(boolean chosenPositive) {
        this.chosenPositive = chosenPositive;
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
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void requestCardToPlay(Trick trick, Integer chosenMinigameNum) {

        trickToPlayTo = trick;

        Card card = trick.cards[0];

        if(card == null) card = new Card(2, -1);

        playableCards = cardHand.getPlayableCards(card, chosenMinigameNum);

        getCardToPlay();
    }

    /**
     * Gives game played card.
     * @param playedCard
     */
    protected void putCard(Card playedCard) {

        if(this.canPlay(playedCard) == false) return;

        this.playableCards.clear();
        this.cardHand.hand.remove(playedCard);

        Integer firstPLayer = trickToPlayTo.firstPlayer;
        Trick tmp = trickToPlayTo;
        trickToPlayTo = null;
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if (tmp.getCards()[i] == null){
                break;
            }
            counter++;
        }

        tmp.addCard(this, playedCard);


        if (counter == 3 && giveServerTrickEnd != null) {
            giveServerTrickEnd.call(tmp, firstPLayer);
        }

    }

    public boolean canPlay(Card card) {
        return this.playableCards.contains(card);
    }

    public Position getPos() {
        return pos;
    }
}
