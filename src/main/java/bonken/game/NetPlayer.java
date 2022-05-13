package bonken.game;

import bonken.utils.Action;
import bonken.utils.Callable;

import java.util.ArrayList;

public class NetPlayer extends GuiPlayer{

    private Action<ArrayList<Integer>> onMinigameRequired;
    private Action<Integer> onMinigameSelected;
    private Callable onCardRequired;

    private boolean miniGameRequired = false;
    private boolean cardRequired = false;

    public NetPlayer(int id, Position pos, String username, Action<ArrayList<Integer>> onMinigameRequired, Callable onCardRequired ) {
        super(id, pos, username, onMinigameRequired, onCardRequired);
        this.username = username;
        this.onMinigameRequired = onMinigameRequired;
        this.onCardRequired = onCardRequired;

    }

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
        onCardRequired.call();
    }
}
