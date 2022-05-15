package bonken.game;

import bonken.net.Client;
import bonken.net.Protocol;
import bonken.net.Server;
import bonken.utils.Action;
import bonken.utils.Callable;
import bonken.utils.DoubleAction;
import javafx.application.Platform;

import java.util.ArrayList;

public class NetPlayer extends Player{

    private Action<ArrayList<Integer>> onMinigameRequired;
    private Action<Integer> onMinigameSelected;
    private DoubleAction<ArrayList<Card>, ArrayList<Card>> onCardRequired;
    DoubleAction<Trick, Integer> giveServerTrickEnd;

    Server server;

    private boolean miniGameRequired = false;
    private boolean cardRequired = false;

    public NetPlayer(int id, Position pos, String username, Action<ArrayList<Integer>> onMinigameRequired, DoubleAction<ArrayList<Card>, ArrayList<Card>> onCardRequired, Server server, DoubleAction<Trick, Integer> giveServerTrickEnd) {
        super(id, pos, giveServerTrickEnd);
        this.username = username;
        this.onMinigameRequired = onMinigameRequired;
        this.onCardRequired = onCardRequired;
        this.server = server;



    }

    //BUDE NA EVENT ZE SERVERU -> jakmile přijde odpovědět od klienta s minihrou!
    public void minigameSelected(Integer minigame) {
        if(miniGameRequired == false) return;
        miniGameRequired = false;
        if (minigame >= 7) {
            chosenPositive = true;
        }

        onMinigameSelected.call(minigame);
    }

    public void cardSelected(String card) {

        if(cardRequired == false) return;
        System.out.println(card);

        Deck deck = server.getGame().getDeck();


        putCard(deck.getSpecificCardByString(card));
    }

    @Override
    public void chooseMinigame(ArrayList<Integer> minigames, Action<Integer> callback) {

        ArrayList<Integer> possibleMinigameChoices = new ArrayList<>();
        for (Integer minigame : minigames) {
            if (chosenPositive) {
                if (minigame < 7) {
                    possibleMinigameChoices.add(minigame);
                }
            } else {
                possibleMinigameChoices.add(minigame);
            }
        }
        miniGameRequired = true;
        this.onMinigameSelected = callback;



        onMinigameRequired.call(possibleMinigameChoices);
    }

    @Override
    protected void getCardToPlay() {
        cardRequired = true;

        Platform.runLater(() -> onCardRequired.call(cardHand.hand, playableCards));

    }

    public String[] getStringHand() {
        String[] stringHand = new String[getCardHand().getHand().size()];
        for (int i = 0; i < getCardHand().getHand().size(); i++) {
            stringHand[i] = getCardHand().getHand().get(i).getImage();
        }

        return stringHand;
    }
}
