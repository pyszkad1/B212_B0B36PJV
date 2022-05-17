package bonken.game;

import bonken.net.Client;
import bonken.utils.Callable;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Class for one round of a game (13 tricks).
 */
public class Round {
    private static final Logger LOGGER = Logger.getLogger(Round.class.getName());
    CardHand[] cardHands;
    PlayerInterface[] players;
    Deck deck;
    ArrayList<Integer> minigames;
    int startingPlayer;
    Integer chosenMiniGameNum;
    Game game;
    MiniGameTrumps chosenMiniGameTrump;
    ArrayList<Card> penaltyCards;
    public ArrayList<Trick> tricks;
    public int trickNum = 0;

    /**
     * Round constructor for offline game.
     * @param game
     * @param deck
     * @param minigames
     * @param players
     */
    public Round(Game game, Deck deck, ArrayList<Integer> minigames, PlayerInterface[] players) {
        this.game = game;
        this.players = players;
        this.deck = deck;
        this.minigames = minigames;
        this.tricks = new ArrayList<>();
        createCardHands();
    }

    private Callable onStatusUpdateNeeded;

    /**
     * Round constructor for net game.
     * @param game
     * @param deck
     * @param minigames
     * @param players
     * @param onStatusUpdateNeeded call when information needs to be sent to client(s)
     */
    public Round(Game game, Deck deck, ArrayList<Integer> minigames, PlayerInterface[] players, Callable onStatusUpdateNeeded) {
        this.game = game;
        this.players = players;
        this.deck = deck;
        this.minigames = minigames;
        this.tricks = new ArrayList<>();
        this.onStatusUpdateNeeded = onStatusUpdateNeeded;
        createCardHands();
    }

    public Trick getCurrentTrick()
    {
        if(tricks.isEmpty()) return null;
        return this.tricks.get(this.trickNum);
    }

    /**
     * Generates 4 card hands of 13 cards from shuffled deck.
     */
    private void createCardHands() {
        ArrayList<ArrayList<Card>> hands = deck.deal();
        cardHands = new CardHand[4];
        for (int i = 0; i < 4; i++) {
            cardHands[i] = new CardHand(hands.get(i));
            cardHands[i].sortHand();
            players[i].setCardHand(cardHands[i]);
        }
    }

    protected void getStartingPlayer() {
        startingPlayer = -1;
        for (PlayerInterface player : players) {
            if (player.isHisTurn()) {
                startingPlayer = player.getId();
            }
        }

    }

    /**
     * Sets chosen minigame.
     * @param chosenMiniGameNum
     */
    public void putMinigame(Integer chosenMiniGameNum) {

        if (chosenMiniGameNum == -1) {
            getNextPlayer();
            chooseGame();
            return;
        }
        minigames.remove(chosenMiniGameNum);
        chosenMiniGameTrump = new MiniGameTrumps(chosenMiniGameNum);

        if (chosenMiniGameNum > 6) {
            LOGGER.info("Chosen POSITIVE minigame.");
        } else {
            MiniGameNegative chosenNegative = new MiniGameNegative(chosenMiniGameNum, deck);
            penaltyCards = chosenNegative.penaltyCards;
            LOGGER.info("Chosen NEGATIVE minigame.");
        }

        this.chosenMiniGameNum = chosenMiniGameNum;

        if (onStatusUpdateNeeded != null) {
            onStatusUpdateNeeded.call();
        }

        int leadingPlayer = (startingPlayer + 3) % 4;
        trickNum = 0;

        playTrick(Position.values()[leadingPlayer]);
    }

    /**
     * Asks player on turn to choose a minigame.
     */
    public void chooseGame() {
        getStartingPlayer();
        players[startingPlayer].chooseMinigame(minigames, minigame -> putMinigame(minigame));
    }

    int[] tricksTaken = {0,0,0,0} ;

    private Callable onRoundFinished;

    public void playRound(Callable onRoundFinished) {

        this.onRoundFinished = onRoundFinished;
        chooseGame();
    }

    /**
     * This method is called, when 4 cards were played in trick.
     */
    private void finishTrick() {
        if ((chosenMiniGameNum == 2 || chosenMiniGameNum == 6) && penaltyCards.size() == 0){
            wrapUp();
            return;
        }

        Trick lastTrick = tricks.get(trickNum);
        int leadingPlayer  = lastTrick.getTrickWinner(lastTrick.getCards());


        tricksTaken[leadingPlayer]++;

        if (trickNum == 12) {
            wrapUp();
            return;
        }

        trickNum++;

        playTrick(Position.values()[leadingPlayer]);
    }

    /**
     * Used on end of round, sends info to game using onRoundFinished call.
     */
    private void wrapUp() {
        int maxTricks = 0;
        int playerNumber = -1;
        for (int i = 0; i < 4; i++) {
            if (tricksTaken[i] > maxTricks){
                maxTricks = tricksTaken[i];
                playerNumber = i;
            }
        }
        getNextPlayer();

        onRoundFinished.call();
    }

    protected void getNextPlayer() {
        for (int i = 0; i < 4; i++) {
            if (players[i].isHisTurn()) {
                players[i].setHisTurn(false);
                players[(i+1)%4].setHisTurn(true);
                break;
            }
        }
    }

    /**
     * Starts a trick.
     * @param leadingPlayer player who took the previous trick (on first trick player on right hand of the minigame chooser)
     */
    private void playTrick(Position leadingPlayer) {
        Trick trick = new Trick(game, this, leadingPlayer, cardHands, chosenMiniGameTrump.trumps);
        tricks.add(trick);

        trick.start( () -> finishTrick() );
    }

    public Integer getChosenMiniGameNum() {
        return chosenMiniGameNum;
    }
}
