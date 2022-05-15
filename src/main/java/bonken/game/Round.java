package bonken.game;

import bonken.utils.Callable;

import java.util.ArrayList;

public class Round {
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

    public Round(Game game, Deck deck, ArrayList<Integer> minigames, PlayerInterface[] players) {
        this.game = game;
        this.players = players;
        this.deck = deck;
        this.minigames = minigames;
        this.tricks = new ArrayList<>();
        createCardHands();
    }

    private Callable onStatusUpdateNeeded;
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

    private void createCardHands() {
        ArrayList<ArrayList<Card>> hands = deck.deal();
        System.out.println(hands.toString());
        cardHands = new CardHand[4];
        for (int i = 0; i < 4; i++) {
            cardHands[i] = new CardHand(hands.get(i));
            cardHands[i].sortHand();
            players[i].setCardHand(cardHands[i]);
        }
    }

    private void getStartingPlayer() {
        startingPlayer = -1;
        for (PlayerInterface player : players) {
            if (player.isHisTurn()) {
                startingPlayer = player.getId();
            }
        }

        System.out.println("Starting PLAYER IS " + startingPlayer);
    }

    private int playerToChooseMinigame;

    public void putMinigame(Integer chosenMiniGameNum) {
        // TODO check if correct player is choosing the minigame

        if (chosenMiniGameNum == -1) {
            getNextPlayer();
            chooseGame();
            return;
        }
        minigames.remove(chosenMiniGameNum);
        chosenMiniGameTrump = new MiniGameTrumps(chosenMiniGameNum);

        //TODO negative/positive minigame choosing
        if (chosenMiniGameNum > 6) {
            System.out.println("Chosen POSITIVE minigame, playing with "
                    + chosenMiniGameTrump.trumpNames[chosenMiniGameTrump.trumps]
                    + " as trumps");
        } else {
            MiniGameNegative chosenNegative = new MiniGameNegative(chosenMiniGameNum, deck);
            penaltyCards = chosenNegative.penaltyCards;
            System.out.println("Chosen NEGATIVE minigame: " + chosenNegative.negativeNames[chosenMiniGameNum]);
        }

        this.chosenMiniGameNum = chosenMiniGameNum;

        if (onStatusUpdateNeeded != null) {
            onStatusUpdateNeeded.call();
        }

        int leadingPlayer = (startingPlayer + 3) % 4;
        System.out.println("LEADING PLAYER IS " + leadingPlayer);
        trickNum = 0;

        playTrick(Position.values()[leadingPlayer]);
    }

    public void chooseGame() {
        getStartingPlayer();
        System.out.println("PLAYER " + startingPlayer + " is choosing a minigame.");
        players[startingPlayer].chooseMinigame(minigames, minigame -> putMinigame(minigame));
    }

    int[] tricksTaken = {0,0,0,0} ;

    private Callable onRoundFinished;

    public void playRound(Callable onRoundFinished) {

        this.onRoundFinished = onRoundFinished;
        chooseGame();
    }



    private void finishTrick() {
        if ((chosenMiniGameNum == 2 || chosenMiniGameNum == 6) && penaltyCards.size() == 0){
            System.out.println("the only card was played");
            wrapUp();
            return;
        }

        Trick lastTrick = tricks.get(trickNum);
        int leadingPlayer  = lastTrick.getTrickWinner(lastTrick.getCards());

        System.out.println("TO NEXT TRICK LEADS PLAYER " + leadingPlayer);

        tricksTaken[leadingPlayer]++;

        if (trickNum == 12) {
            wrapUp();
            return;
        }

        trickNum++;

        playTrick(Position.values()[leadingPlayer]);
    }

    private void wrapUp() {
        //QUICK COUNTER! - deletable
        int maxTricks = 0;
        int playerNumber = -1;
        System.out.println("Tricks of players:");
        for (int i = 0; i < 4; i++) {
            System.out.println("Player " + i + " got " + tricksTaken[i]);
            if (tricksTaken[i] > maxTricks){
                maxTricks = tricksTaken[i];
                playerNumber = i;
            }
        }
        System.out.println("Player " + playerNumber + " won with " + maxTricks + " tricks");
        getNextPlayer();

        onRoundFinished.call();
    }

    private void getNextPlayer() {
        for (int i = 0; i < 4; i++) {
            if (players[i].isHisTurn()) {
                players[i].setHisTurn(false);
                players[(i+1)%4].setHisTurn(true);           // TODO should check the rules
                break;
            }
        }
    }

    private void playTrick(Position leadingPlayer) {
        Trick trick = new Trick(game, this, leadingPlayer, cardHands, chosenMiniGameTrump.trumps);
        tricks.add(trick);

        trick.start( () -> finishTrick() );
    }

    public Integer getChosenMiniGameNum() {
        return chosenMiniGameNum;
    }
}
