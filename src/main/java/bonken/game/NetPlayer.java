package bonken.game;

import bonken.net.Client;
import bonken.net.Protocol;
import bonken.net.Server;
import bonken.utils.Action;
import bonken.utils.Callable;

import java.util.ArrayList;

public class NetPlayer extends Player{

    private Action<ArrayList<Integer>> onMinigameRequired;
    private Action<Integer> onMinigameSelected;
    private Action<ArrayList<Card>> onCardRequired;

    Server server;

    private boolean miniGameRequired = false;
    private boolean cardRequired = false;

    public NetPlayer(int id, Position pos, String username, Action<ArrayList<Integer>> onMinigameRequired, Action<ArrayList<Card>> onCardRequired, Server server) {
        super(id, pos);
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

    public void cardSelected(Card card) {
        if(cardRequired == false) return;
        System.out.println(card.toString());
        putCard(card);
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

        onCardRequired.call(playableCards);
    }
}
