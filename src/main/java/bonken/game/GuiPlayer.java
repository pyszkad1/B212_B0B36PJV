package bonken.game;

import bonken.utils.Action;
import bonken.utils.Callable;

import java.util.ArrayList;

public class GuiPlayer extends Player{

    private Action<ArrayList<Integer>> onMinigameRequired;
    private Action<Integer> onMinigameSelected;
    private Callable onCardRequired;

    private boolean miniGameRequired = false;
    private boolean cardRequired = false;

    public GuiPlayer(int id, Position pos, String username, Action<ArrayList<Integer>> onMinigameRequired, Callable onCardRequired ) {
        super(id, pos);
        this.username = username;
        this.onMinigameRequired = onMinigameRequired;
        this.onCardRequired = onCardRequired;

    }

    public void minigameSelected(Integer minigame) {

        if(miniGameRequired == false) return;
        miniGameRequired = false;
        onMinigameSelected.call(minigame);
    }

    public void cardSelected(Card card) {
        if(cardRequired == false) return;
        System.out.println(card.toString());
        putCard(card);
    }

    @Override
    public void chooseMinigame(ArrayList<Integer> minigames, Action<Integer> callback) {
        miniGameRequired = true;
        this.onMinigameSelected = callback;
        onMinigameRequired.call(minigames);
    }

    @Override
    protected void getCardToPlay() {
        cardRequired = true;
        onCardRequired.call();
    }
}