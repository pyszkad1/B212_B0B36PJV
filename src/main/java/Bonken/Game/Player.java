package Bonken.Game;

public class Player {
    final String username;
    final int id;
    CardHand hand;
    int score;
    boolean flagAllCheck;
    boolean hisTurn;

    public Player(String username, int id) {
        this.username = username;
        this.id = id;
    }

}
