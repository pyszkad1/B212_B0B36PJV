package bonken.game;

import bonken.utils.Action;
import bonken.utils.Callable;

import java.util.ArrayList;

public class GuiPlayer extends Player{

    private Action<ArrayList<Integer>> onMinigameRequired;
    private Action<Integer> onMinigameSelected;
    private Callable onCardRequired;
    Game game;

    private boolean miniGameRequired = false;
    private boolean cardRequired = false;

    public GuiPlayer(int id, Position pos, String username, Action<ArrayList<Integer>> onMinigameRequired, Callable onCardRequired) {
        super(id, pos);
        this.username = username;
        this.onMinigameRequired = onMinigameRequired;
        this.onCardRequired = onCardRequired;

    }

    public void setGame(Game game){
        this.game = game;

    }

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

        Deck deck = game.getDeck();

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
        onCardRequired.call();
    }


    public String[] getStringHand() {
        String[] stringHand = new String[getCardHand().getHand().size()];
        for (int i = 0; i < getCardHand().getHand().size(); i++) {
            stringHand[i] = getCardHand().getHand().get(i).getImage();
        }

        return stringHand;
    }

    public String[] getStringPlayableCards() {
        ArrayList<Card> playableCards = getCardHand().getPlayableCards(game.getCurrentRound().getCurrentTrick().getCards()[0], game.getCurrentRound().chosenMiniGameNum);
        String[] stringPlayableCards = new String[playableCards.size()];
        for (int i = 0; i < playableCards.size(); i++) {
            stringPlayableCards[i] = playableCards.get(i).getImage();
        }
        return stringPlayableCards;
    }
}
