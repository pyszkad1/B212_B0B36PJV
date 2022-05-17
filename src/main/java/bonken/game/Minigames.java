package bonken.game;

/**
 * Enum for all minigames.
 */
public enum Minigames {
    KingsAndJacks (0, "K & J"),
    Queens (1, "Q"),
    KingOfHearts (2, "K♥"),
    NoHearts (3, "No ♥"),
    NoTricks (4, "No tricks"),
    LastTrick (5, "Last trick"),
    BeerCard (6, "Beer card"),
    Clubs (7, "♣"),
    Diamonds (8, "♦"),
    Hearts (9, "♥"),
    Spades (10, "♠"),
    NoTrumps ( 11, "NoTrumps");

    public final int num;
    public final String name;

    Minigames(int num, String name) {

        this.num = num;
        this.name = name;
    }

}
