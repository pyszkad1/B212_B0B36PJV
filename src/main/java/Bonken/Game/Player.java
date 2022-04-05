package Bonken.Game;

public class Player {
    String username;
    CardHand hand;
    int score;
    boolean hasCards;
    boolean flagAllCheck;
    boolean hisTurn;

    public Player(String username) {
        this.username = username;
    }

}
